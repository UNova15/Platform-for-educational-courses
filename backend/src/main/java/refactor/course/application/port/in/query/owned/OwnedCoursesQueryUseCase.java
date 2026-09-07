package refactor.course.application.port.in.query.owned;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.user.Account;

import java.util.List;

@Validated
public interface OwnedCoursesQueryUseCase {
    TeacherCourseView findTeachersCourseById(@NotNull Id<Account> teacherId, @NotNull Id<Course> courseId);

    TeachersModuleView findTeachersModuleById(@NotNull Id<Account> teacherId, @NotNull Id<CourseModule> moduleId);

    TeachersLessonView findTeachersLessonById(@NotNull Id<Account> teacherId, @NotNull Id<Lesson> lessonId);

    TeachersTestView findTeachersTestById(@NotNull Id<Account> teacherId, @NotNull Id<Test> testId);

    List<OwnedCoursesView> findTeachersCourses(@NotNull Id<Account> teacherId);
}
