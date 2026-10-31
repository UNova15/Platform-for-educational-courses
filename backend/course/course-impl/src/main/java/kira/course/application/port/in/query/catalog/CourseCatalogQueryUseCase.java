package kira.course.application.port.in.query.catalog;

import common.adapter.CursorResponse;
import common.domain.entity.Id;
import jakarta.validation.Valid;
import kira.course.domain.course.Course;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCatalogQueryUseCase {
    CursorResponse<CourseCursorView, Long> findCoursesByCursor(@Valid CursorCourseQuery query);

    CourseInfo findCourseInfoById(Id<Course> courseId);
}
