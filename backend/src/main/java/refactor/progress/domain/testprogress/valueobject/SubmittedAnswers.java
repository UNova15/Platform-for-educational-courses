package refactor.progress.domain.testprogress.valueobject;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.AnswerOption;
import refactor.progress.domain.markers.Question;

import java.util.Set;

public record SubmittedAnswers(Id<Question> questionId, Set<Id<AnswerOption>> optionsIds) {}
