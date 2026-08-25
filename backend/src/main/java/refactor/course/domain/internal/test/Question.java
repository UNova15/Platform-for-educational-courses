package refactor.course.domain.internal.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.domain.DomainModificationException;
import refactor.common.exception.domain.DomainValidationException;
import refactor.course.domain.internal.test.valueobject.QuestionContent;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    public static final int MAX_OPTION_COUNT = 10;
    public static final int MIN_OPTION_COUNT = 2;

    private final Id<Question> id;
    private int orderIndex;
    private QuestionContent question;

    @Getter(AccessLevel.NONE)
    private Set<AnswerOption> answerOptions;

    public Set<AnswerOption> answerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }

    public static Question createNew(QuestionContent question, Set<AnswerOption> options, int orderIndex) {
        if (question == null || orderIndex < 0 || !isValidOptions(options)) {
            throw new DomainValidationException("Incorrect data to create question");
        }
        return new Question(null, orderIndex, question, new HashSet<>(options));
    }

    public static Question restore(Id<Question> id, QuestionContent question, int orderIndex, Set<AnswerOption> answerOptions) {
        return new Question(id, orderIndex, question, new HashSet<>(answerOptions));
    }

    public Set<Long> calculateCorrectOptionsIds() {
        return answerOptions.stream()
                .filter(AnswerOption::isCorrect)
                .map(AnswerOption::id)
                .collect(Collectors.toSet());
    }

    private static boolean isValidOptions(Set<AnswerOption> options) {
        if (options == null || options.size() > MAX_OPTION_COUNT || options.size() < MIN_OPTION_COUNT) {
            return false;
        }
        return options.stream().anyMatch(AnswerOption::isCorrect);
    }

    public void addOption(AnswerOption answerOption) {
        if (answerOption == null || answerOptions.size() == MAX_OPTION_COUNT) {
            throw new DomainModificationException("Incorrect data to add answer option");
        }
        answerOptions.add(answerOption);
    }
}
