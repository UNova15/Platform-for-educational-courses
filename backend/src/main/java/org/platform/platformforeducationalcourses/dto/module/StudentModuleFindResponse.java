package org.platform.platformforeducationalcourses.dto.module;

import java.util.List;
import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;

public record StudentModuleFindResponse(
        Long id,
        Long courseId,
        String title,
        String description,
        int orderIndex,
        List<StudentLessonFindResponse> lessons,
        List<StudentTestFindResponse> tests) {}
