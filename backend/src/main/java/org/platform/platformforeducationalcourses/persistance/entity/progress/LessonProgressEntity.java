package org.platform.platformforeducationalcourses.persistance.entity.progress;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("lessons_progress")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonProgressEntity {
    @Id
    private Long id;

    private long userId;
    private long lessonId;
    private final LocalDateTime completedAt;
}
