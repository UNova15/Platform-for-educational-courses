package org.platform.platformforeducationalcourses.dto.course.createdto;

import org.platform.platformforeducationalcourses.dto.test.TestCreateDto;

import java.util.List;

public record CourseModuleCreateDto(
        String title,
        String description,
        int orderIndex,
        List<LessonsCourseCreateDto> lessons,
        List<TestCreateDto> tests
) {
    public CourseModuleCreateDto {
        lessons = lessons == null ? List.of() : lessons;
        tests = tests == null ? List.of() : tests;
    }
}
