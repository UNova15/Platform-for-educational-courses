package refactor.progress.application.port.out.persistance.testattempt;

import refactor.progress.domain.testprogress.TestAttempt;

public interface TestAttemptSavePort {
    void save(TestAttempt attempt);
}
