package kira.progress.application.port.out.persistance.testattempt;

import common.domain.entity.Id;
import java.util.Optional;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;

public interface TestAttemptLoadPort {
    Optional<TestAttempt> loadByUserIdAndTestId(Id<User> userId, Id<Test> testId);

    boolean isAttemptExist(Id<User> studentId, Id<Test> testId);
}
