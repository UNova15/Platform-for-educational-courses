package course.application.service;

import lombok.RequiredArgsConstructor;
import refactor.common.wrapper.CursorResponse;
import org.springframework.stereotype.Service;
import refactor.course.implementation.application.port.in.query.catalog.CourseCursorView;
import refactor.course.implementation.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import refactor.course.implementation.application.port.in.query.catalog.CursorCourseQuery;
import refactor.course.implementation.application.port.out.persistance.query.CourseCatalogQueryPort;

@Service
@RequiredArgsConstructor
public class CourseCatalogQueryService implements CourseCatalogQueryUseCase {
    private final CourseCatalogQueryPort queryPort;

    @Override
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query) {
        return queryPort.findCoursesByCursor(query);
    }
}
