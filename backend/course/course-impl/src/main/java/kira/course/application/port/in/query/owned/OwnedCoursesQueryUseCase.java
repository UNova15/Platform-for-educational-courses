package kira.course.application.port.in.query.owned;

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

// Намеренно выделил общие ошибки для предотвращения их дублирования и унификации проверок в сервис слое
// В случае появления уникальных ошибок/изменения логики проверок у каких либо методов разделить ошибки и логику
// проверок
@Validated
public interface OwnedCoursesQueryUseCase {
    enum CourseQueryError {
        RESOURCE_NOT_FOUND,
        ACCESS_DENIED
    }

    Result<TeacherCourseView, CourseQueryError> findTeachersCourseById(
            @NotNull Id<User> teacherId, @NotNull Id<Course> courseId);

    Result<TeachersModuleView, CourseQueryError> findTeachersModuleById(
            @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);

    Result<TeachersLessonView, CourseQueryError> findTeachersLessonById(
            @NotNull Id<User> teacherId, @NotNull Id<Lesson> lessonId);

    Result<FullTestView, CourseQueryError> findFullTestById(@NotNull Id<User> teacherId, @NotNull Id<Test> testId);

    List<OwnedCoursesView> findTeachersCourses(@NotNull Id<User> teacherId);
}
