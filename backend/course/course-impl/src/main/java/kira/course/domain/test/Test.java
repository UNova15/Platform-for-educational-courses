package kira.course.domain.test;

import common.domain.entity.Id;
import common.domain.exception.DomainStateException;
import common.domain.exception.DomainValidationException;
import common.domain.exception.LengthViolationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
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
    private CourseContentStatus status;

    public Set<Question> questions() {
        return Collections.unmodifiableSet(questions);
    }

    private static void validateQuestions(Set<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new DomainValidationException("Empty questions to create test", CourseAggregateType.TEST);
        }

        if (questions.size() < MIN_QUESTION_COUNT || questions.size() > MAX_QUESTION_COUNT) {
            throw new LengthViolationException(
                    "Invalid question count to create test", questions.size(), MAX_QUESTION_COUNT, MIN_QUESTION_COUNT);
        }
    }

    public static Test create(
            Id<CourseModule> moduleId, Title title, Description description, Set<Question> questions, int orderIndex) {
        if (moduleId == null || orderIndex < 0 || title == null) {
            throw new DomainValidationException("Empty or negative data to create test", CourseAggregateType.TEST);
        }
        validateQuestions(questions);

        return new Test(
                null, moduleId, orderIndex, title, description, new HashSet<>(questions), CourseContentStatus.DRAFT);
    }

    public static Test restore(
            Id<Test> id,
            Id<CourseModule> moduleId,
            Title title,
            Description description,
            int orderIndex,
            Set<Question> questions,
            CourseContentStatus status) {
        return new Test(id, moduleId, orderIndex, title, description, new HashSet<>(questions), status);
    }

    public void update(Title title, Description description, int orderIndex, Set<Question> questions) {
        if (title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update test", CourseAggregateType.TEST);
        }
        validateQuestions(questions);

        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.questions = questions;
    }

    public boolean isDraft() {
        return status == CourseContentStatus.DRAFT;
    }

    public boolean isPublish() {
        return status == CourseContentStatus.PUBLISHED;
    }

    private boolean canPublish() {
        return status.canTransitionTo(CourseContentStatus.PUBLISHED);
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish test", CourseAggregateType.TEST, id)
                    .withContext("status", status);
        }
        status = CourseContentStatus.PUBLISHED;
    }
}
