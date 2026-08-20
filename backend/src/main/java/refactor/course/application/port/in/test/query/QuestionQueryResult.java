package refactor.course.application.port.in.test.query;

import java.util.List;

public record QuestionQueryResult(
        long questionId, int orderIndex, String question, List<AnswerQueryResult> answers) {}
