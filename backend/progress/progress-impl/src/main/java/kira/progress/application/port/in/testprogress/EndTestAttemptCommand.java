package kira.progress.application.port.in.testprogress;

import common.domain.Id;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.valueobject.SubmittedAnswers;

import java.util.Set;

public record EndTestAttemptCommand(Id<User> userId, Id<Test> testId, Set<SubmittedAnswers> answers) {}
