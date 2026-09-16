package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.progress.application.port.in.testprogress.EndTestAttemptCommand;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.valueobject.SubmittedAnswers;

import java.util.Set;
import java.util.stream.Collectors;

public class CommandFactory {

    public static EndTestAttemptCommand toEndTestAttemptCommand(Set<EndTestRequest> request, long resourceId, long userId) {
        Id<User> studentId = Id.of(userId);
        Id<Test> testId = Id.of(resourceId);

        Set<SubmittedAnswers> answers = request.stream()
                .map(value -> {
                    Id<Question> questionId = Id.of(value.questionId());
                    Set<Id<AnswerOption>> selectedIds = value.selectedOptions().stream()
                            .map(Id::<AnswerOption>of)
                            .collect(Collectors.toSet());
                    return new SubmittedAnswers(questionId, selectedIds);
                })
                .collect(Collectors.toSet());

        return new EndTestAttemptCommand(studentId, testId, answers);
    }
}
