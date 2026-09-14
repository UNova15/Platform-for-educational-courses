package course.application.port.in.query.learning;

import refactor.course.implementation.domain.lesson.ContentType;

public record StudentsLessonView(
        long id,
        long moduleId,
        long courseId,
        int orderIndex,
        boolean mandatory,
        String title,
        ContentType type,
        String content) {}
