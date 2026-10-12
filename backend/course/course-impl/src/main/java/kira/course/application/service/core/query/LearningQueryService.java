package kira.course.application.service.core.query;

import common.domain.Id;
import java.util.List;

import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.query.learning.*;
import kira.course.application.port.out.external.EnrollmentCheckPort;
import kira.course.application.port.out.external.TestCompletionCheckPort;
import kira.course.application.port.out.external.UserEnrollmentProviderPort;
import kira.course.application.port.out.persistance.query.LearningQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Status;
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
                .findCourseViewByIdAndStatus(courseId, Status.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, requesterId, courseId);
        }

        return view;
    }

    @Override
    public StudentsModuleView findStudentsModule(Id<User> requesterId, Id<CourseModule> moduleId) {
        StudentsModuleView view = learningQueryPort
                .findModuleViewByIdAndStatus(moduleId, Status.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.MODULE, moduleId));

        Id<Course> courseId = Id.of(view.courseId());
        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, requesterId, moduleId);
        }

        return view;
    }

    @Override
    public StudentsLessonView findStudentsLesson(Id<User> requesterId, Id<Lesson> lessonId) {
        StudentsLessonView view = learningQueryPort
                .findLessonViewByIdAndStatus(lessonId, Status.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.LESSON, lessonId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.LESSON, requesterId, lessonId);
        }
        return view;
    }

    @Override
    public StudentsTestView findStudentsTest(Id<User> requesterId, Id<Test> testId) {
        StudentsTestView view = learningQueryPort
                .findTestViewByIdAndStatus(testId, Status.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.TEST, testId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.TEST, requesterId, testId);
        }

        boolean isCompleted = testCompletionCheckPort.isTestCompleted(requesterId, testId);
        boolean isSolving = testCompletionCheckPort.isStudentSolvingTest(requesterId, testId);

        if (!isSolving && !isCompleted) {
            throw new RuleConflictException(
                    "The test has not been completed and is not being carried out right now",
                    CourseResourceName.TEST,
                    testId);
        }
        return view;
    }
}
