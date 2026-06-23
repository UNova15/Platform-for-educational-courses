package org.platform.platformforeducationalcourses.domain.progress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.courseutil.ScoreCalculator;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.platform.platformforeducationalcourses.validator.SubmissionValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Table("test_submissions")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestSubmission {
    @Id
    private Long id;
    private final long userId;
    private final long testId;
    private final LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int score;

    @MappedCollection(idColumn = "test_submission_id")
    Set<TestAnswer> answers;

    public Set<TestAnswer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    public static TestSubmission createNew(long userId, long testId) {
        if (userId < 0 || testId < 0) {
            throw new IllegalArgumentException("Incorrect data to create test submission");
        }

        return new TestSubmission(null, userId, testId, LocalDateTime.now(), null, 0, null);
    }

    public void submitAnswers(List<AnswerPostDto> answers, Map<Long, Question> questionsOrderById,
                              ScoreCalculator calculator, SubmissionValidator validator) {
        if (completedAt != null) {
            throw new IllegalArgumentException("Attempt has already been saved");
        }
        validator.validate(answers, questionsOrderById);

        int testScore = calculator.calculate(answers, questionsOrderById);

        Set<TestAnswer> testAnswers = answers.stream()
                .map(answer -> TestAnswer.createNew(
                        answer.questionId(),
                        answer.optionId())
                )
                .collect(Collectors.toSet());

        completedAt = LocalDateTime.now();
        this.answers = testAnswers;
        this.score = testScore;
    }
}
