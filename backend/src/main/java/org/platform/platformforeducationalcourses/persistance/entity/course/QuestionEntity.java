package org.platform.platformforeducationalcourses.persistance.entity.course;

import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "test_questions")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionEntity {
    @Id
    private final Long id;

    private final Long testId;
    private String question;
    private int orderIndex;

    @MappedCollection(idColumn = "question_id")
    private Set<AnswerOptionEntity> answerOptions;

    public Set<AnswerOptionEntity> getAnswerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }
}
