package kira.course.application.port.in.query.learning;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LearningQueryUseCase {
    enum CourseQueryError {
        COURSE_NOT_FOUND,
        ACCESS_DENY
    }

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(@NotNull Id<User> requesterId);

    Result<StudentsCourseView, CourseQueryError> findStudentsCourse(
            @NotNull Id<User> requesterId, @NotNull Id<Course> courseId);

    enum ModuleQueryError {
        MODULE_NOT_FOUND,
        ACCESS_DENY
    }

    Result<StudentsModuleView, ModuleQueryError> findStudentsModule(
            @NotNull Id<User> requesterId, @NotNull Id<CourseModule> moduleId);

    enum LessonQueryError {
        LESSON_NOT_FOUND,
        ACCESS_DENY
    }

    Result<StudentsLessonView, LessonQueryError> findStudentsLesson(
            @NotNull Id<User> requesterId, @NotNull Id<Lesson> lessonId);

    enum TestQueryError {
        TEST_NOT_FOUND,
        ACCESS_DENY,
        TEST_NOT_STARTED
    }

    Result<StudentsTestView, TestQueryError> findStudentsTest(@NotNull Id<User> requesterId, @NotNull Id<Test> testId);
}
