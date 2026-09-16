package kira.course.application.port.in.query.catalog;

import common.wrapper.CursorResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCatalogQueryUseCase {
    CursorResponse<CourseCursorView, Long> findCoursesByCursor(@Valid CursorCourseQuery query);
}
