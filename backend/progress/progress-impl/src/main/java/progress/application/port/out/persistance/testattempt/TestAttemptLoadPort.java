package progress.application.port.out.persistance.testattempt;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;
import refactor.progress.implementation.domain.testprogress.TestAttempt;

import java.util.Optional;

public interface TestAttemptLoadPort {
    Optional<TestAttempt> loadByUserIdAndTestId(Id<User> userId, Id<Test> testId);

    boolean isAttemptExist(Id<User> studentId, Id<Test> testId);
}
