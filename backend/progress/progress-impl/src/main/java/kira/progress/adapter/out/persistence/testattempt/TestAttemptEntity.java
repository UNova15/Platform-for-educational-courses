package kira.progress.adapter.out.persistence.testattempt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Table("progress.test_submissions")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class TestAttemptEntity {

    @Id
    private final Long id;

    private final long userId;
    private final long testId;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;

    private final int score;

    @MappedCollection(idColumn = "test_submission_id")
    private final Set<TestAnswerEntity> answers;
}
