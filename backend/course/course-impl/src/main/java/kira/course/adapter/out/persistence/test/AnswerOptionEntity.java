package kira.course.adapter.out.persistence.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "course.question_options")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class AnswerOptionEntity {
    @Id
    private final Long id;

    private final String option;
    private final boolean isCorrect;
}
