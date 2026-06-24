package org.platform.platformforeducationalcourses.dto.course.find;

import java.util.List;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;

public record CourseModuleFindResponse(
        long id,
        String title,
        String description,
        int orderIndex,
        List<TestFindResponse> tests,
        List<LessonFindResponse> lessons) {}
