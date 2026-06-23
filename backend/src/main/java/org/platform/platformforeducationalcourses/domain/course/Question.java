package org.platform.platformforeducationalcourses.domain.course;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "test_questions")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    @Id
    private final Long id;
    private final Long testId;
    private String question;
    private int orderIndex;

    @MappedCollection(idColumn = "question_id")
    private Set<AnswerOption> answerOptions;

    public Set<AnswerOption> getAnswerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }

    public static Question createNew(String question, Set<AnswerOption> answerOptions, int orderIndex) {
        if (question == null || question.isBlank() || answerOptions == null || answerOptions.isEmpty() || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create question");
        }
        return new Question(null, null, question, orderIndex, answerOptions);
    }

    //TODO fix it
    public Long getCorrectAnswerOptionsId() {
        return answerOptions.stream()
                .filter(AnswerOption::isCorrect)
                .map(AnswerOption::getId)
                .findFirst().orElseThrow(IllegalStateException::new);
    }


}

