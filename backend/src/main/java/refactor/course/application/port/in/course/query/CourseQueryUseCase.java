package refactor.course.application.port.in.course.query;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import refactor.common.domain.Id;
import refactor.common.wrapper.CursorPageResponse;
import org.springframework.validation.annotation.Validated;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;


@Validated
public interface CourseQueryUseCase {
    OwnedCoursesListView findTeachersCourses(@NotNull Id<User> teacherId);

    CursorPageResponse<CourseCursorView> findCoursesByCursor(@Valid CursorCourseQuery query);

    TeacherCourseView findTeachersCourseById(@NotNull Id<User> teacherId, @NotNull Id<Course> courseId);

    StudentCourseView findStudentCourseById(@NotNull Id<User> requesterId, @NotNull Id<Course> courseId);
}
