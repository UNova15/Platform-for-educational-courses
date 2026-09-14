package course.application.port.in.query.owned;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.Test;
import refactor.course.implementation.domain.markers.Account;

import java.util.List;

@Validated
public interface OwnedCoursesQueryUseCase {
    TeacherCourseView findTeachersCourseById(@NotNull Id<Account> teacherId, @NotNull Id<Course> courseId);

    TeachersModuleView findTeachersModuleById(@NotNull Id<Account> teacherId, @NotNull Id<CourseModule> moduleId);

    TeachersLessonView findTeachersLessonById(@NotNull Id<Account> teacherId, @NotNull Id<Lesson> lessonId);

    FullTestView findFullTestById(@NotNull Id<Account> teacherId, @NotNull Id<Test> testId);

    List<OwnedCoursesView> findTeachersCourses(@NotNull Id<Account> teacherId);
}
