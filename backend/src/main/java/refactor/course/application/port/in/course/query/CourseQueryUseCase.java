package refactor.course.application.port.in.course.query;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import refactor.common.wrapper.CursorPageResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CourseQueryUseCase {
    List<CourseQueryResult> findTeachersCoursesInfo(@PositiveOrZero long teacherId);

    CursorPageResponse<CourseCursorResult> findCoursesByCursor(@Valid CursorCourseQuery query);
}
