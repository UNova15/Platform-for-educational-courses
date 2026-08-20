package refactor.course.application.port.in.test.query;

import java.util.List;

public record TestQueryResult(long testId, String description, int orderIndex, List<QuestionQueryResult> questions) {}
