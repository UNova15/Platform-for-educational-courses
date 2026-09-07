package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.course.Course;
import refactor.common.wrapper.CursorResponse;
import refactor.course.application.port.in.query.catalog.CourseCursorView;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseAssembler {
    private final CourseMapper courseMapper;

    public CursorResponse<CourseCursorView> createPageResponseWithCoursePage(Page<Course> page) {
        List<CourseCursorView> courseCursorView = page.map(courseMapper::toCoursePage).toList();

        return new CursorResponse<>(
                courseCursorView, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
