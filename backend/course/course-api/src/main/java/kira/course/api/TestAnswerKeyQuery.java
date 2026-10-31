package kira.course.api;

import java.util.Optional;

public interface TestAnswerKeyQuery {
    Optional<CourseAnswerKey> findAnswerKeyByTestId(long testId);
}
