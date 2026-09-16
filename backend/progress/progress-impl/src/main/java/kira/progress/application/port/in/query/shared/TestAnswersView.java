package kira.progress.application.port.in.query.shared;

import java.util.List;

public record TestAnswersView(long testId, List<QuestionAnswer> answers) {

    public record QuestionAnswer(long questionId, List<Long> selectedOptionsIds) {}
}
