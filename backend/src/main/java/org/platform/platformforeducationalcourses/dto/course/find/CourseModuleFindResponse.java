package org.platform.platformforeducationalcourses.dto.course.find;

import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;

import java.util.List;

public record CourseModuleFindResponse(
        long id,
        String title,
        String description,
        int orderIndex,
        List<TestFindResponse> tests,
        List<LessonFindResponse> lessons
) {
}
