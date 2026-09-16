package kira.progress.application.port.out.persistance.testattempt;


import kira.progress.domain.testprogress.TestAttempt;

public interface TestAttemptSavePort {
    void save(TestAttempt attempt);
}
