package refactor.progress.application.port.in.testprogress;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;
import refactor.progress.domain.testprogress.valueobject.SubmittedAnswers;

import java.util.Set;

public record EndTestAttemptCommand(Id<User> userId, Id<Test> testId, Set<SubmittedAnswers> answers) {}
