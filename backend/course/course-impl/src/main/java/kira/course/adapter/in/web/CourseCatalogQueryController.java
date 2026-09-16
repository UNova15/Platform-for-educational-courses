package kira.course.adapter.in.web;

import common.wrapper.CursorResponse;
import kira.course.domain.course.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;

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
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(required = false) Tag tag) {
        CursorCourseQuery query = new CursorCourseQuery(cursorId, limit, tag);

        return queryUseCase.findCoursesByCursor(query);
    }
}
