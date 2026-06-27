package org.platform.platformforeducationalcourses.persistance.entity.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "question_options")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerOptionEntity {
    @Id
    private Long id;

    private Long questionId;
    private String option;
    private boolean isCorrect;
}
