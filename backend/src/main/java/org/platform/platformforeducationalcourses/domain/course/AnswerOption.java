package org.platform.platformforeducationalcourses.domain.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerOption {
    private Long id;

    private Long questionId;
    private String option;
    private boolean isCorrect;

    public static AnswerOption createNew(String option, boolean isCorrect) {
        if (option == null || option.isBlank()) {
            throw new IllegalArgumentException("Incorrect data to create question option");
        }

        return new AnswerOption(null, null, option, isCorrect);
    }

    public static AnswerOption restore(Long id, Long questionId, String option, boolean isCorrect) {
        return new AnswerOption(id, questionId, option, isCorrect);
    }
}
