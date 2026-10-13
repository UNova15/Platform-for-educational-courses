package kira.course.application.port.in.query.learning;

import kira.course.domain.lesson.LessonContentType;

public record StudentsLessonView(
        long id,
        long moduleId,
        long courseId,
        int orderIndex,
        boolean mandatory,
        String title,
        LessonContentType type,
        String content) {}
