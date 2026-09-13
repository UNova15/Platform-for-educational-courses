package refactor.course.application.port.in.query.learning;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;

import java.util.List;

@Validated
public interface LearningQueryUseCase {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(@NotNull Id<Account> requesterId);

    StudentsCourseView findStudentsCourse(@NotNull Id<Account> requesterId, @NotNull Id<Course> courseId);

    StudentsModuleView findStudentsModule(@NotNull Id<Account> requesterId, @NotNull Id<CourseModule> moduleId);

    StudentsLessonView findStudentsLesson(@NotNull Id<Account> requesterId, @NotNull Id<Lesson> lessonId);

    StudentsTestView findStudentsTest(@NotNull Id<Account> requesterId, @NotNull Id<Test> testId);
}
