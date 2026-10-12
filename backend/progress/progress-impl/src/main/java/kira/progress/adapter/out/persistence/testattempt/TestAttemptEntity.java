package kira.progress.adapter.out.persistence.testattempt;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "test_submissions", schema = "progress")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class TestAttemptEntity {

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
