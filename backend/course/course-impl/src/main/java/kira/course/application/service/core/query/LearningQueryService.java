package kira.course.application.service.core.query;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import java.util.List;
import kira.course.application.port.in.query.learning.*;
import kira.course.application.port.out.external.EnrollmentCheckPort;
import kira.course.application.port.out.external.TestCompletionCheckPort;
import kira.course.application.port.out.external.UserEnrollmentProviderPort;
import kira.course.application.port.out.persistance.query.LearningQueryPort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LearningQueryService implements LearningQueryUseCase {
    private final LearningQueryPort learningQueryPort;
    private final EnrollmentCheckPort enrollmentCheckPort;
    private final UserEnrollmentProviderPort enrollmentCoursesPort;
    private final TestCompletionCheckPort testCompletionCheckPort;

    @Override
    public List<UserCourseView> findCoursesThatUsersIsEnrolledIn(Id<User> requesterId) {
        List<Id<Course>> coursesIds = enrollmentCoursesPort.findCoursesIdsThatUsersIsEnrolledIn(requesterId);

        if (coursesIds.isEmpty()) {
            return List.of();
        }
        return learningQueryPort.findCoursesThatUsersIsEnrolledIn(coursesIds);
    }

    @Override
    public StudentsCourseView findStudentsCourse(Id<User> requesterId, Id<Course> courseId) {
        StudentsCourseView view = learningQueryPort
                .findCourseViewByIdAndStatus(courseId, CourseContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.COURSE, requesterId, courseId);
        }

        return view;
    }

    @Override
    public StudentsModuleView findStudentsModule(Id<User> requesterId, Id<CourseModule> moduleId) {
        StudentsModuleView view = learningQueryPort
                .findModuleViewByIdAndStatus(moduleId, CourseContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId));

        Id<Course> courseId = Id.of(view.courseId());
        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, requesterId, moduleId);
        }

        return view;
    }

    @Override
    public StudentsLessonView findStudentsLesson(Id<User> requesterId, Id<Lesson> lessonId) {
        StudentsLessonView view = learningQueryPort
                .findLessonViewByIdAndStatus(lessonId, CourseContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.LESSON, lessonId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.LESSON, requesterId, lessonId);
        }
        return view;
    }

    @Override
    public StudentsTestView findStudentsTest(Id<User> requesterId, Id<Test> testId) {
        StudentsTestView view = learningQueryPort
                .findTestViewByIdAndStatus(testId, CourseContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, requesterId, testId);
        }

        boolean isCompleted = testCompletionCheckPort.isTestCompleted(requesterId, testId);
        boolean isSolving = testCompletionCheckPort.isStudentSolvingTest(requesterId, testId);

        if (!isSolving && !isCompleted) {
            throw new RuleConflictException(
                    "The test has not been completed and is not being carried out right now",
                    CourseAggregateType.TEST,
                    testId);
        }
        return view;
    }
}
