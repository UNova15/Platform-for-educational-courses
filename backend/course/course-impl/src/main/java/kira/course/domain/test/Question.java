package kira.course.domain.test;

import common.domain.Id;
import common.exception.core.DomainValidationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import kira.course.domain.test.valueobject.QuestionContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import common.exception.core.LengthViolationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    public static final int MAX_OPTION_COUNT = 10;
    public static final int MIN_OPTION_COUNT = 2;

    private final Id<Question> id;
    private int orderIndex;
    private QuestionContent content;

    @Getter(AccessLevel.NONE)
    private Set<AnswerOption> answerOptions;

    public Set<AnswerOption> answerOptions() {
        return Collections.unmodifiableSet(answerOptions);
    }

    public static Question create(QuestionContent question, Set<AnswerOption> options, int orderIndex) {
        if (question == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to create question");
        }
        validateOptions(options);

        return new Question(null, orderIndex, question, new HashSet<>(options));
    }

    public static Question restore(
            Id<Question> id, QuestionContent question, int orderIndex, Set<AnswerOption> answerOptions) {
        return new Question(id, orderIndex, question, new HashSet<>(answerOptions));
    }

    private static void validateOptions(Set<AnswerOption> options) {
        if (options == null || options.isEmpty()) {
            throw new DomainValidationException("Empty options to create question");
        }

        if (options.size() > MAX_OPTION_COUNT || options.size() < MIN_OPTION_COUNT) {
            throw new LengthViolationException(
                    "Incorrect options count in question", options.size(), MAX_OPTION_COUNT, MIN_OPTION_COUNT);
        }

        if (options.stream().anyMatch(AnswerOption::isCorrect)) {
            throw new DomainValidationException("The question must contain at least one correct answer");
        }
    }
}
