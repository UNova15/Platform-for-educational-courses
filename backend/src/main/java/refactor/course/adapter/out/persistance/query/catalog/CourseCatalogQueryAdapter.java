package refactor.course.adapter.out.persistance.query.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.common.util.CursorFactory;
import refactor.common.wrapper.CursorResponse;
import refactor.course.application.port.in.query.catalog.CourseCursorView;
import refactor.course.application.port.in.query.catalog.CursorCourseQuery;
import refactor.course.application.port.out.persistance.query.CourseCatalogQueryPort;

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
