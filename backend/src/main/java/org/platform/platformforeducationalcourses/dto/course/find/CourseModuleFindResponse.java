package org.platform.platformforeducationalcourses.dto.course.find;

import java.util.List;
import refactor.course.application.port.in.lesson.query.LessonQueryResult;
import refactor.course.application.port.in.test.query.TestQueryResult;

public record CourseModuleFindResponse(
        long id,
        String title,
        String description,
        int orderIndex,
        List<TestQueryResult> tests,
        List<LessonQueryResult> lessons) {}
