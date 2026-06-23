package org.platform.platformforeducationalcourses.domain.progress;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("lessons_progress")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonProgress {
    @Id
    private Long id;
    private long userId;
    private long lessonId;
    private final LocalDateTime completedAt;

    public static LessonProgress createNew(long userId, long lessonId) {
        if (userId < 0 || lessonId < 0) {
            throw new IllegalArgumentException("Incorrect data to create lesson progress");
        }
        return new LessonProgress(null, userId, lessonId, LocalDateTime.now());
    }
}
