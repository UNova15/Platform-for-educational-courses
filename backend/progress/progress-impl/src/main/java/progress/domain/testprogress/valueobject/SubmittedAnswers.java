package progress.domain.testprogress.valueobject;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.AnswerOption;
import refactor.progress.implementation.domain.markers.Question;

import java.util.Set;

public record SubmittedAnswers(Id<Question> questionId, Set<Id<AnswerOption>> optionsIds) {}
