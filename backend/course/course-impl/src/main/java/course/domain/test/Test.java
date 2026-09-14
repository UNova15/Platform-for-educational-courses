package course.domain.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.common.exception.DomainModificationException;
import course.domain.common.Description;
import course.domain.common.Title;
import course.domain.module.CourseModule;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Test {
    public static final int MAX_QUESTION_COUNT = 50;
    public static final int MIN_QUESTION_COUNT = 2;

    private final Id<Test> id;
    private Id<CourseModule> moduleId;
    private int orderIndex;
    private Title title;
    private Description description;
    private Set<Question> questions;

    public Set<Question> questions() {
        return Collections.unmodifiableSet(questions);
    }

    public static Test createNew(
            Id<CourseModule> moduleId, Title title, Description description, Set<Question> questions, int orderIndex) {
        if (moduleId == null
                || orderIndex < 0
                || title == null
                || questions == null
                || questions.size() < MIN_QUESTION_COUNT
                || questions.size() > MAX_QUESTION_COUNT) {
            throw new DomainValidationException("Incorrect data to create test");
        }
        return new Test(null, moduleId, orderIndex, title, description, new HashSet<>(questions));
    }

    public static Test restore(
            Id<Test> id,
            Id<CourseModule> moduleId,
            Title title,
            Description description,
            int orderIndex,
            Set<Question> questions) {
        return new Test(id, moduleId, orderIndex, title, description, new HashSet<>(questions));
    }

    public void update(Title title, Description description, int orderIndex, Set<Question> questions) {
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
