package refactor.course.application.port.in.course.query;

import refactor.course.domain.internal.course.Tag;
import refactor.course.domain.internal.lesson.ContentType;

import java.time.LocalDateTime;
import java.util.List;

public record StudentCourseView(
        long id,
        long teacherId,
        Tag tag,
        LocalDateTime createdAt,
        String title,
        String description,
        List<Module> modules) {

    public record Module(
            long id,
            long courseId,
            int orderIndex,
            String title,
            String description,
            List<Lesson> lessons,
            List<Test> tests) {}

    public record Lesson(
            long id,
            long moduleId,
            int orderIndex,
            boolean mandatory,
            String title,
            ContentType type,
            String content) {}

    public record Test(long id, long moduleId, int orderIndex, String title, String description) {}
}
