package kira.course.adapter.out.persistence.query.catalog;

import common.util.CursorFactory;
import common.wrapper.CursorResponse;
import java.util.List;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.application.port.out.persistance.query.CourseCatalogQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CourseCatalogQueryAdapter implements CourseCatalogQueryPort {
    private final CourseDynamicQueryRepository dynamicQueryRepository;

    @Override
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query) {
        List<CourseCursorView> view = dynamicQueryRepository.findCoursesByCursor(query);

        return CursorFactory.createFrom(view, CourseCursorView::id, query.limit());
    }
}
