package refactor.progress.application.port.out.persistance;

import refactor.progress.domain.testprogress.TestAttempt;

public interface TestAttemptSavePort {
    void save(TestAttempt attempt);
}
