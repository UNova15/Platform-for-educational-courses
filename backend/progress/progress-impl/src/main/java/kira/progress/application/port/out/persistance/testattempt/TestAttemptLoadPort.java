package kira.progress.application.port.out.persistance.testattempt;

import common.domain.Id;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;

import java.util.Optional;

public interface TestAttemptLoadPort {
    Optional<TestAttempt> loadByUserIdAndTestId(Id<User> userId, Id<Test> testId);

    boolean isAttemptExist(Id<User> studentId, Id<Test> testId);
}
