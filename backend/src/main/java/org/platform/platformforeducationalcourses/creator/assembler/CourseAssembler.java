package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.course.Course;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.CourseCursorResult;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseAssembler {
    private final CourseMapper courseMapper;

    public CursorPageResponse<CourseCursorResult> createPageResponseWithCoursePage(Page<Course> page) {
        List<CourseCursorResult> courseCursorResult = page.map(courseMapper::toCoursePage).toList();

        return new CursorPageResponse<>(
                courseCursorResult, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
