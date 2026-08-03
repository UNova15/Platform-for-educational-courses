package org.platform.platformforeducationalcourses.persistance.entity.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.domain.course.ContentType;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("lessons")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonEntity {
    @Id
    private final Long id;

    private final Long moduleId;
    private String title;
    private ContentType type;
    private String content;
    private int orderIndex;
    private boolean mandatory;

    public static LessonEntity fromLesson(Lesson lesson) {
        return new LessonEntity(
                lesson.getId(),
                lesson.getModuleId(),
                lesson.getTitle(),
                lesson.getType(),
                lesson.getContent(),
                lesson.getOrderIndex(),
                lesson.isMandatory());
    }
}
