package course.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refactor.common.wrapper.CursorResponse;
import course.application.port.in.query.catalog.CourseCursorView;
import course.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import course.application.port.in.query.catalog.CursorCourseQuery;
import refactor.course.implementation.domain.course.Tag;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CourseCatalogQueryController {
    private final CourseCatalogQueryUseCase queryUseCase;

    // поиск всех доступных курсов для пользователя/преподавателя. Доступно всем ролям и не аунтефицированным
    // пользователям
    @GetMapping
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Tag tag) {
        CursorCourseQuery query = new CursorCourseQuery(cursorId, limit, tag);

        return queryUseCase.findCoursesByCursor(query);
    }
}
