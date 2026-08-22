package refactor.course.application.port.in.course.query;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import refactor.common.wrapper.CursorPageResponse;
import org.springframework.validation.annotation.Validated;


@Validated
public interface CourseQueryUseCase {
    OwnedCoursesListView findTeachersCourses(@PositiveOrZero long teacherId);

    CursorPageResponse<CourseCursorView> findCoursesByCursor(@Valid CursorCourseQuery query);

    TeacherCourseView findTeachersCourseById(@PositiveOrZero long teacherId, @PositiveOrZero long courseId);

    StudentCourseView findStudentCourseById(@PositiveOrZero long requesterId, @PositiveOrZero long courseId);
}
