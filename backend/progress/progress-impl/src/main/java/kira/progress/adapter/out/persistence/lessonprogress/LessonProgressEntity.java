package kira.progress.adapter.out.persistence.lessonprogress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("progress.lessons_progress")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class LessonProgressEntity {

    record LessonProgressKey(long userId, long lessonId) {}

    @Id
    private final LessonProgressKey id;

    private final LocalDateTime completedAt;
}
