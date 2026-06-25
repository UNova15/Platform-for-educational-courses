package org.platform.platformforeducationalcourses.dto.course.createdto;

import java.util.List;
import org.platform.platformforeducationalcourses.dto.test.TestCreateDto;

public record CourseModuleCreateDto(
        String title,
        String description,
        int orderIndex,
        List<LessonsCourseCreateDto> lessons,
        List<TestCreateDto> tests) {
    public CourseModuleCreateDto {
        lessons = lessons == null ? List.of() : lessons;
        tests = tests == null ? List.of() : tests;
    }
}
