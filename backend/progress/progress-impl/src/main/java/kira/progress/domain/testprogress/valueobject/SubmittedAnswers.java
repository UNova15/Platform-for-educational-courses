package kira.progress.domain.testprogress.valueobject;

import common.domain.Id;
import java.util.Set;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;

public record SubmittedAnswers(Id<Question> questionId, Set<Id<AnswerOption>> optionsIds) {}
