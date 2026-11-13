package kira.progress.api;

public interface TestStatusQuery {
    boolean hasCompletedTest(long studentId, long testId);

    boolean isSolvingTest(long studentId, long testId);
}
