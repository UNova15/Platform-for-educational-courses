package kira.course.application.port.in.query.owned;

import common.domain.entity.Id;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import org.springframework.validation.annotation.Validated;

@Validated
public interface OwnedCoursesQueryUseCase {

    TeacherCourseView findTeachersCourseById(@NotNull Id<User> teacherId, @NotNull Id<Course> courseId);

    TeachersModuleView findTeachersModuleById(@NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);

    TeachersLessonView findTeachersLessonById(@NotNull Id<User> teacherId, @NotNull Id<Lesson> lessonId);

    FullTestView findFullTestById(@NotNull Id<User> teacherId, @NotNull Id<Test> testId);

    List<OwnedCoursesView> findTeachersCourses(@NotNull Id<User> teacherId);
}
