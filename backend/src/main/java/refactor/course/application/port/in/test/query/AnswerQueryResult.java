package refactor.course.application.port.in.test.query;

public record AnswerQueryResult(long questionId, String option, boolean isCorrect) {}
