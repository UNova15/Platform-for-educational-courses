package org.platform.platformforeducationalcourses.domain.progress;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAnswer {
    private Long id;

    private final Long testSubmissionId;
    private final long questionId;
    private final List<Long> answerIds;

    public static TestAnswer createNew(long questionId, List<Long> answerIds) {
        if (questionId < 0 || answerIds.stream().anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Incorrect data to create test answer");
        }
        return new TestAnswer(null, null, questionId, answerIds);
    }
}
