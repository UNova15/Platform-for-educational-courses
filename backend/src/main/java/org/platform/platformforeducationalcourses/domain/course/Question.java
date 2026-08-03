package org.platform.platformforeducationalcourses.domain.course;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    private final Long id;

    private final Long testId;
    private String question;
    private int orderIndex;

    @Getter(value = AccessLevel.NONE)
    private Set<AnswerOption> answerOptions;

    public Set<AnswerOption> getAnswerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }

    public static Question createNew(String question, Set<AnswerOption> answerOptions, int orderIndex) {
        if (question == null
                || question.isBlank()
                || answerOptions == null
                || answerOptions.isEmpty()
                || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create question");
        }
        return new Question(null, null, question, orderIndex, answerOptions);
    }

    public static Question restore(
            Long id, Long testId, String question, int orderIndex, Set<AnswerOption> answerOptions) {
        return new Question(id, testId, question, orderIndex, answerOptions);
    }

    // TODO добавить функция выбора множества правильных ответов
    public Set<Long> getCorrectAnswerOptionsIds() {
        return answerOptions.stream()
                .filter(AnswerOption::isCorrect)
                .map(AnswerOption::getId)
                .collect(Collectors.toSet());
    }
}
