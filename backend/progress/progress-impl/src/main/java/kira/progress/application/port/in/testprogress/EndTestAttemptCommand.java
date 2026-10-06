package kira.progress.application.port.in.testprogress;

import common.domain.Id;
import java.util.Set;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.valueobject.SubmittedAnswers;

public record EndTestAttemptCommand(Id<User> userId, Id<Test> testId, Set<SubmittedAnswers> answers) {}
