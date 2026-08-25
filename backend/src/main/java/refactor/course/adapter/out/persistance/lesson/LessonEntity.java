package refactor.course.adapter.out.persistance.lesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refactor.course.domain.internal.lesson.ContentType;
import refactor.course.domain.internal.lesson.Lesson;
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
