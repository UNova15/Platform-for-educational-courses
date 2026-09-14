package course.application.port.in.query.learning;

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
public interface LearningQueryUseCase {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(@NotNull Id<Account> requesterId);

    StudentsCourseView findStudentsCourse(@NotNull Id<Account> requesterId, @NotNull Id<Course> courseId);

    StudentsModuleView findStudentsModule(@NotNull Id<Account> requesterId, @NotNull Id<CourseModule> moduleId);

    StudentsLessonView findStudentsLesson(@NotNull Id<Account> requesterId, @NotNull Id<Lesson> lessonId);

    StudentsTestView findStudentsTest(@NotNull Id<Account> requesterId, @NotNull Id<Test> testId);
}
