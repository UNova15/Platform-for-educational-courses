package kira.course.adapter.out.persistence.lesson;

import kira.course.domain.lesson.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("course.lessons")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class LessonEntity {
    @Id
    private final Long id;

    private final Long moduleId;
    private final String title;
    private final ContentType type;
    private final String content;
    private final int orderIndex;
    private final boolean mandatory;
}
