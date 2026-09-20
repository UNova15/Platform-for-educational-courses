package kira.progress.application.port.out.persistance.query;

import common.domain.Id;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface TestAttemptStatusQueryPort {
    boolean isTestCompleted(Id<User> studentId, Id<Test> testId);

    boolean isStudentSolvingTest(Id<User> studentId, Id<Test> testId);
}
