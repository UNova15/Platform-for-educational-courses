package refactor.course.application.port.out.persistance.query;

import refactor.common.wrapper.CursorResponse;
import refactor.course.application.port.in.query.catalog.CourseCursorView;
import refactor.course.application.port.in.query.catalog.CursorCourseQuery;

public interface CourseCatalogQueryPort {

    CursorResponse<CourseCursorView,Long> findCoursesByCursor(CursorCourseQuery query);
}
