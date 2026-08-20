package refactor.course.application.port.in.module.query;

public record ModuleQueryResult(long id, long courseId, String title, String description, int orderIndex) {}
