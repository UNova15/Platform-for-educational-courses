package refactor.course.application.port.in.query.catalog;

import jakarta.validation.Valid;
import refactor.common.wrapper.CursorResponse;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCatalogQueryUseCase {
    CursorResponse<CourseCursorView, Long> findCoursesByCursor(@Valid CursorCourseQuery query);
}
