package org.platform.platformforeducationalcourses.persistance.entity.progress;

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
public class TestAnswerEntity {
    @Id
    private Long id;

    private final Long testSubmissionId;
    private final long questionId;
    private final long answerId;
}
