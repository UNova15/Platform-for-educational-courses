package kira.course.application.port.in.query.owned;


import kira.course.domain.lesson.ContentType;

public record TeachersLessonView(
        long id,
        long moduleId,
        long teacherId,
        String title,
        ContentType type,
        String content,
        int orderIndex,
        boolean mandatory) {}
