package kira.progress.adapter.out.persistence.lessonprogress;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "lessons_progress",schema = "progress")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class LessonProgressEntity {

    record LessonProgressKey(long userId, long lessonId) {}

    @Id
    private final LessonProgressKey id;

    private final LocalDateTime completedAt;
}
