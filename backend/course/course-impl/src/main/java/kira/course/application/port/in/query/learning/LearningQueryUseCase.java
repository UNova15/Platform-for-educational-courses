package kira.course.application.port.in.query.learning;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface LearningQueryUseCase {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(@NotNull Id<User> requesterId);

    StudentsCourseView findStudentsCourse(@NotNull Id<User> requesterId, @NotNull Id<Course> courseId);

    StudentsModuleView findStudentsModule(@NotNull Id<User> requesterId, @NotNull Id<CourseModule> moduleId);

    StudentsLessonView findStudentsLesson(@NotNull Id<User> requesterId, @NotNull Id<Lesson> lessonId);

    StudentsTestView findStudentsTest(@NotNull Id<User> requesterId, @NotNull Id<Test> testId);
}
