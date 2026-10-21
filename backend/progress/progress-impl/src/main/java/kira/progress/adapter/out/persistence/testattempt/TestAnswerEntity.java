package kira.progress.adapter.out.persistence.testattempt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "test_answers", schema = "progress")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class TestAnswerEntity {
    private final long optionId;
    private final long questionId;
}
