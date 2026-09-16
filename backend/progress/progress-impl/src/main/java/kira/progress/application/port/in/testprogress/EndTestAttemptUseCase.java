package kira.progress.application.port.in.testprogress;

public interface EndTestAttemptUseCase {
    TestResult endTestAttempt(EndTestAttemptCommand command);
}
