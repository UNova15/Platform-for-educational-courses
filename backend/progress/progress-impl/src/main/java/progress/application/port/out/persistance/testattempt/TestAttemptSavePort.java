package progress.application.port.out.persistance.testattempt;

import refactor.progress.implementation.domain.testprogress.TestAttempt;

public interface TestAttemptSavePort {
    void save(TestAttempt attempt);
}
