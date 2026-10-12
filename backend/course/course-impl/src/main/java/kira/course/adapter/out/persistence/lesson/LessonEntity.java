package kira.course.adapter.out.persistence.lesson;

import kira.course.domain.lesson.ContentType;
import kira.course.domain.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "lessons", schema = "course")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class LessonEntity {
    @Id
    private final Long id;

    private final Long moduleId;
    private final String title;
    private final ContentType type;
    private final String content;
    private final int orderIndex;
    private final boolean mandatory;
    private final Status status;
}
