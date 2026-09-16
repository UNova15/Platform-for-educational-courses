package kira.course.application.port.out.persistance.query;


import common.wrapper.CursorResponse;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;

public interface CourseCatalogQueryPort {

    CursorResponse<CourseCursorView,Long> findCoursesByCursor(CursorCourseQuery query);
}
