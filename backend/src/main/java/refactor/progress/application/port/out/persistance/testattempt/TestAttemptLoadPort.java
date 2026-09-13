package refactor.progress.application.port.out.persistance.testattempt;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;
import refactor.progress.domain.testprogress.TestAttempt;

import java.util.Optional;

public interface TestAttemptLoadPort {
    Optional<TestAttempt> loadByUserIdAndTestId(Id<User> userId, Id<Test> testId);

    boolean isAttemptExist(Id<User> studentId, Id<Test> testId);
}
