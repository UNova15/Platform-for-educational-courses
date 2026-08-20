package refactor.course.domain.test;

import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;
import refactor.common.exception.domain.DomainModificationException;
import refactor.course.domain.question.Question;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Test {
    public static final int MAX_QUESTION_COUNT = 50;

    private final Long id;
    private Long moduleId;
    private int orderIndex;
    private TestTitle title;
    private TestDescription description;
    private Set<Question> questions;

    public Set<Question> questions() {
        return Collections.unmodifiableSet(questions);
    }

    public static Test createNew(
            long moduleId, TestTitle title, TestDescription description, Set<Question> questions, int orderIndex) {
        if (moduleId < 0
                || orderIndex < 0
                || title == null
                || questions == null
                || questions.isEmpty()
                || questions.size() > MAX_QUESTION_COUNT) {
            throw new DomainValidationException("Incorrect data to create test");
        }
        return new Test(null, moduleId, orderIndex, title, description, questions);
    }

    public static Test restore(
            Long id,
            Long moduleId,
            TestTitle title,
            TestDescription description,
            int orderIndex,
            Set<Question> questions) {
        return new Test(id, moduleId, orderIndex, title, description, questions);
    }

    public void update(TestTitle title, TestDescription description, int orderIndex, Set<Question> questions) {
        if (title == null || orderIndex < 0 || questions == null || questions.isEmpty()) {
            throw new DomainModificationException("Incorrect data to update test");
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        if (question == null || questions.size() >= MAX_QUESTION_COUNT) {
            throw new DomainModificationException("Incorrect data to add question in test");
        }
        questions.add(question);
    }
}
