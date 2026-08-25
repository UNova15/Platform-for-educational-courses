package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.internal.course.Course;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.CourseCursorView;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseAssembler {
    private final CourseMapper courseMapper;

    public CursorPageResponse<CourseCursorView> createPageResponseWithCoursePage(Page<Course> page) {
        List<CourseCursorView> courseCursorView = page.map(courseMapper::toCoursePage).toList();

        return new CursorPageResponse<>(
                courseCursorView, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
