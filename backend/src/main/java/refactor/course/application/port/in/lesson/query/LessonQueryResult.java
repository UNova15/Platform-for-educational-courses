package refactor.course.application.port.in.lesson.query;

import refactor.course.domain.lesson.ContentType;

public record LessonQueryResult(
        long id, long moduleId, String title, ContentType type, String content, int orderIndex, boolean mandatory) {}
