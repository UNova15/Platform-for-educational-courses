package kira.course.application.service.core.query;

import common.domain.Id;
import java.util.List;
import java.util.Optional;

import common.domain.Result;
import kira.course.application.port.in.query.learning.*;
import kira.course.application.port.out.external.EnrollmentCheckPort;
import kira.course.application.port.out.external.TestCompletionCheckPort;
import kira.course.application.port.out.external.UserEnrollmentProviderPort;
import kira.course.application.port.out.persistance.query.LearningQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningQueryService implements LearningQueryUseCase {
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
    public Result<StudentsCourseView, CourseQueryError> findStudentsCourse(Id<User> requesterId, Id<Course> courseId) {
        Optional<StudentsCourseView> loaded = learningQueryPort.findStudentsCourseViewById(courseId);

        if (loaded.isEmpty()) {
            return Result.failure(CourseQueryError.COURSE_NOT_FOUND);
        }
        var view = loaded.get();

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            return Result.failure(CourseQueryError.ACCESS_DENY);
        }

        return Result.success(view);
    }

    @Override
    public Result<StudentsModuleView, ModuleQueryError> findStudentsModule(
            Id<User> requesterId, Id<CourseModule> moduleId) {
        Optional<StudentsModuleView> loaded = learningQueryPort.findStudentsModuleViewById(moduleId);

        if (loaded.isEmpty()) {
            return Result.failure(ModuleQueryError.MODULE_NOT_FOUND);
        }
        var view = loaded.get();

        Id<Course> courseId = Id.of(view.courseId());
        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            return Result.failure(ModuleQueryError.ACCESS_DENY);
        }

        return Result.success(view);
    }

    @Override
    public Result<StudentsLessonView, LessonQueryError> findStudentsLesson(Id<User> requesterId, Id<Lesson> lessonId) {
        Optional<StudentsLessonView> loaded = learningQueryPort.findStudentsLessonViewById(lessonId);

        if (loaded.isEmpty()) {
            return Result.failure(LessonQueryError.LESSON_NOT_FOUND);
        }

        var view = loaded.get();
        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            return Result.failure(LessonQueryError.ACCESS_DENY);
        }
        return Result.success(view);
    }

    @Override
    public Result<StudentsTestView, TestQueryError> findStudentsTest(Id<User> requesterId, Id<Test> testId) {
        Optional<StudentsTestView> loaded = learningQueryPort.findStudentsTestView(testId);

        if (loaded.isEmpty()) {
            return Result.failure(TestQueryError.TEST_NOT_FOUND);
        }

        var view = loaded.get();
        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            return Result.failure(TestQueryError.ACCESS_DENY);
        }

        boolean isCompleted = testCompletionCheckPort.isTestCompleted(requesterId, testId);
        boolean isSolving = testCompletionCheckPort.isStudentSolvingTest(requesterId, testId);

        if (!isSolving && !isCompleted) {
            return Result.failure(TestQueryError.TEST_NOT_STARTED);
        }

        return Result.success(view);
    }
}
