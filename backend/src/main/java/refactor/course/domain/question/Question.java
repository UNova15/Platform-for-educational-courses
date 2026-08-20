package refactor.course.domain.question;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainModificationException;
import refactor.common.exception.domain.DomainValidationException;
import refactor.course.domain.option.AnswerOption;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    public static final int MAX_OPTION_COUNT = 10;
    public static final int MIN_OPTION_COUNT = 2;

    private final Long id;
    private int orderIndex;
    private QuestionTitle question;

    @Getter(AccessLevel.NONE)
    private Set<AnswerOption> answerOptions;

    public Set<AnswerOption> answerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }

    public static Question createNew(QuestionTitle question, Set<AnswerOption> options, int orderIndex) {
        if (question == null
                || orderIndex < 0
                || options == null
                || options.isEmpty()
                || options.size() > MAX_OPTION_COUNT) {
            throw new DomainValidationException("Incorrect data to create question");
        }
        return new Question(null, orderIndex, question, options);
    }

    public static Question restore(Long id, QuestionTitle question, int orderIndex, Set<AnswerOption> answerOptions) {
        return new Question(id, orderIndex, question, answerOptions);
    }

    public Set<Long> getCorrectAnswerOptionsIds() {
        return answerOptions.stream()
                .filter(AnswerOption::isCorrect)
                .map(AnswerOption::id)
                .collect(Collectors.toSet());
    }

    public void addOption(AnswerOption answerOption) {
        if (answerOption == null || answerOptions.size() == MAX_OPTION_COUNT) {
            throw new DomainModificationException("Incorrect data to add answer option");
        }
        answerOptions.add(answerOption);
    }
}
