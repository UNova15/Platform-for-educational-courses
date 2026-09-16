package kira.progress.domain.testprogress.valueobject;


import common.domain.Id;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;

import java.util.Set;

public record SubmittedAnswers(Id<Question> questionId, Set<Id<AnswerOption>> optionsIds) {}
