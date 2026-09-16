package kira.course.adapter.out.persistance.query.catalog;

import common.util.CursorFactory;
import common.wrapper.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.application.port.out.persistance.query.CourseCatalogQueryPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseCatalogQueryAdapter implements CourseCatalogQueryPort {
    private final CourseDynamicQueryRepository dynamicQueryRepository;

    @Override
    public CursorResponse<CourseCursorView,Long> findCoursesByCursor(CursorCourseQuery query) {
        List<CourseCursorView> view = dynamicQueryRepository.findCoursesByCursor(query);

        return CursorFactory.createFrom(view, CourseCursorView::id, query.limit());
    }
}
