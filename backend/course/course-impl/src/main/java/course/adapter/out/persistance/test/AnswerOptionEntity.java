package course.adapter.out.persistance.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "question_options")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class AnswerOptionEntity {
    @Id
    private Long id;

    private String option;
    private boolean isCorrect;
}
