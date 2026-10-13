package kira.course.application.port.in.query.owned;

import kira.course.domain.lesson.LessonContentType;
import kira.course.domain.shared.CourseContentStatus;

public record TeachersLessonView(
        long id,
        long moduleId,
        long teacherId,
        String title,
        LessonContentType type,
        String content,
        int orderIndex,
        CourseContentStatus status,
        boolean mandatory) {}
