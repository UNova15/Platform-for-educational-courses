package org.platform.platformforeducationalcourses.persistance.entity.progress;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("test_submissions")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestSubmissionEntity {
    @Id
    private Long id;

    private final long userId;
    private final long testId;
    private final LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int score;

    @MappedCollection(idColumn = "test_submission_id")
    Set<TestAnswerEntity> answers;

    public Set<TestAnswerEntity> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }
}
