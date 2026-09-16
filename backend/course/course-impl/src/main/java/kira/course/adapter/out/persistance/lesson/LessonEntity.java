package kira.course.adapter.out.persistance.lesson;

import kira.course.domain.lesson.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("lessons")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class LessonEntity {
    @Id
    private final Long id;

    private final Long moduleId;
    private String title;
    private ContentType type;
    private String content;
    private int orderIndex;
    private boolean mandatory;
}
