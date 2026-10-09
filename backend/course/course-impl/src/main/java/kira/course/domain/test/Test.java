package kira.course.domain.test;

import common.domain.Id;
import common.exception.core.DomainStateException;
import common.exception.core.DomainValidationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import common.exception.named.LengthViolationException;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Status;
import kira.course.domain.shared.Title;
import kira.course.domain.module.CourseModule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

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
    private Status status;

    public Set<Question> questions() {
        return Collections.unmodifiableSet(questions);
    }

    private static void validateQuestions(Set<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new DomainValidationException("Empty questions to create test");
        }

        if (questions.size() < MIN_QUESTION_COUNT || questions.size() > MAX_QUESTION_COUNT) {
            throw new LengthViolationException(
                    "Invalid question count to create test",
                    "Test",
                    questions.size(),
                    MAX_QUESTION_COUNT,
                    MIN_QUESTION_COUNT);
        }
    }

    public static Test create(
            Id<CourseModule> moduleId, Title title, Description description, Set<Question> questions, int orderIndex) {
        if (moduleId == null || orderIndex < 0 || title == null) {
            throw new DomainValidationException("Empty or negative data to create test");
        }
        validateQuestions(questions);

        return new Test(null, moduleId, orderIndex, title, description, new HashSet<>(questions), Status.DRAFT);
    }

    public static Test restore(
            Id<Test> id,
            Id<CourseModule> moduleId,
            Title title,
            Description description,
            int orderIndex,
            Set<Question> questions,
            Status status) {
        return new Test(id, moduleId, orderIndex, title, description, new HashSet<>(questions), status);
    }

    public void update(Title title, Description description, int orderIndex, Set<Question> questions) {
        if (title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update test");
        }
        validateQuestions(questions);

        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.questions = questions;
    }

    public boolean isDraft(){
        return status == Status.DRAFT;
    }

    public boolean canPublish() {
        return status.canTransitionTo(Status.PUBLISHED);
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish test", id).withContext("status", status);
        }
        status = Status.PUBLISHED;
    }
}
