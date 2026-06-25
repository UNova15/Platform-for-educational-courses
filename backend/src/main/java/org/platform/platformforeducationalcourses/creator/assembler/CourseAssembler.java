package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.dto.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseAssembler {
    private final CourseMapper courseMapper;

    public PageResponse<CoursePage> createPageResponseWithCoursePage(Page<Course> page) {
        List<CoursePage> coursePage = page.map(courseMapper::toCoursePage).toList();

        return new PageResponse<>(
                coursePage, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
