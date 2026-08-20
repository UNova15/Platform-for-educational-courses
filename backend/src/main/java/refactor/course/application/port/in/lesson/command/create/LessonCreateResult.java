package refactor.course.application.port.in.lesson.command.create;

public record LessonCreateResult(long id, long moduleId, String title) {}
