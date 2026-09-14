package progress.domain.lessonprogress;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.User;

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
