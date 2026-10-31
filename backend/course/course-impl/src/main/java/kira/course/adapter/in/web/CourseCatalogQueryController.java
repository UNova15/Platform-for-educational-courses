package kira.course.adapter.in.web;

import common.adapter.CursorResponse;
import common.domain.entity.Id;
import kira.course.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CourseInfo;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.domain.course.Course;
import kira.course.domain.course.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
class CourseCatalogQueryController {
    private final CourseCatalogQueryUseCase queryUseCase;

    // поиск всех доступных курсов для пользователя/преподавателя. Доступно всем ролям и не аунтефицированным
    // пользователям
    @GetMapping
    public CursorResponse<CourseCursorView, Long> getCoursesByCursor(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(required = false) Tag tag) {
        CursorCourseQuery query = new CursorCourseQuery(cursorId, limit, tag);

        return queryUseCase.findCoursesByCursor(query);
    }

    @GetMapping("/{courseId}")
    public CourseInfo getCourseInfo(@PathVariable("courseId") long resourceId) {
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findCourseInfoById(courseId);
    }
}
