package kira.course.adapter.out.persistence.test;

import java.util.Collections;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "course.test_questions")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class QuestionEntity {
    @Id
    private final Long id;

    private final String question;
    private final int orderIndex;

    @Getter(AccessLevel.NONE)
    @MappedCollection(idColumn = "question_id")
    private final Set<AnswerOptionEntity> answerOptions;

    public Set<AnswerOptionEntity> answerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }
}
