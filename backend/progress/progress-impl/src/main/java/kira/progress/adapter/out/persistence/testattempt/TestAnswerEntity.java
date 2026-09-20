package kira.progress.adapter.out.persistence.testattempt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

@Table("progress.test_answers")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class TestAnswerEntity {
    private final long optionId;
    private final long questionId;
}
