package kira.course.application.service;

import common.wrapper.CursorResponse;
import kira.course.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.application.port.out.persistance.query.CourseCatalogQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCatalogQueryService implements CourseCatalogQueryUseCase {
    private final CourseCatalogQueryPort queryPort;

    @Override
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query) {
        return queryPort.findCoursesByCursor(query);
    }
}
