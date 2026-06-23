package org.platform.platformforeducationalcourses.dto.module;

import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;

import java.util.List;

public record StudentModuleFindResponse(
        Long id,
        Long courseId,
        String title,
        String description,
        int orderIndex,
        List<StudentLessonFindResponse> lessons,
        List<StudentTestFindResponse> tests
) {
}
