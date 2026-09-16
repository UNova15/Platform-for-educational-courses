package kira.progress.domain.lessonprogress;

import java.time.LocalDateTime;

import common.domain.Id;
import common.exception.DomainValidationException;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonProgress {
    private Id<LessonProgress> id;

    private Id<User> userId;
    private Id<Lesson> lessonId;
    private final LocalDateTime completedAt;

    public static LessonProgress createNew(Id<User> userId, Id<Lesson> lessonId) {
        if (userId == null || lessonId == null) {
            throw new DomainValidationException("Incorrect data to create lesson progress");
        }
        return new LessonProgress(null, userId, lessonId, LocalDateTime.now());
    }
}
