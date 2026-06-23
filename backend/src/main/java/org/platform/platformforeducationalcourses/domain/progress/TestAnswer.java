package org.platform.platformforeducationalcourses.domain.progress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("test_answers")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAnswer {
    @Id
    private Long id;
    private final Long testSubmissionId;
    private final long questionId;
    private final long answerId;

    public static TestAnswer createNew(long questionId, long answerId) {
        if ( questionId < 0 || answerId < 0) {
            throw new IllegalArgumentException("Incorrect data to create test answer");
        }
        return new TestAnswer(null, null, questionId, answerId);
    }
}
