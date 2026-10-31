package kira.course.application.port.out.persistance.query;

import common.adapter.CursorResponse;
import common.domain.entity.Id;
import java.util.Optional;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CourseInfo;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.shared.CourseContentStatus;

public interface CourseCatalogQueryPort {

    CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query);

    Optional<CourseInfo> findCourseByIdAndStatus(
            Id<Course> courseId, CourseStatus courseStatus, CourseContentStatus moduleStatus);
}
