package progress.application.port.in.testprogress;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;
import refactor.progress.implementation.domain.testprogress.valueobject.SubmittedAnswers;

import java.util.Set;

public record EndTestAttemptCommand(Id<User> userId, Id<Test> testId, Set<SubmittedAnswers> answers) {}
