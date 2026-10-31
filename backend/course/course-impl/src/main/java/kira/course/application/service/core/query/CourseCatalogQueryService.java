package kira.course.application.service.core.query;

import common.adapter.CursorResponse;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.query.catalog.CourseCatalogQueryUseCase;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CourseInfo;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.application.port.out.persistance.query.CourseCatalogQueryPort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.shared.CourseContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CourseCatalogQueryService implements CourseCatalogQueryUseCase {
    private final CourseCatalogQueryPort queryPort;

    @Override
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query) {
        return queryPort.findCoursesByCursor(query);
    }

    @Override
    public CourseInfo findCourseInfoById(Id<Course> courseId) {
        return queryPort
                .findCourseByIdAndStatus(courseId, CourseStatus.PUBLISHED, CourseContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));
    }
}
