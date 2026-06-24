package org.platform.platformforeducationalcourses.domain.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("lessons")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    private final Long id;

    private final Long moduleId;
    private String title;
    private ContentType type;
    private String content;
    private int orderIndex;
    private boolean mandatory;

    public static Lesson createNew(
            long moduleId, String title, ContentType type, String content, int orderIndex, boolean mandatory) {
        if (moduleId < 0
                || title == null
                || title.isBlank()
                || type == null
                || content == null
                || content.isBlank()
                || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create lesson");
        }
        return new Lesson(null, moduleId, title, type, content, orderIndex, mandatory);
    }

    public void update(String title, ContentType type, String content, int orderIndex, boolean mandatory) {
        if (title == null
                || title.isBlank()
                || type == null
                || content == null
                || content.isBlank()
                || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create lesson");
        }

        this.content = content;
        this.mandatory = mandatory;
        this.orderIndex = orderIndex;
        this.type = type;
        this.title = title;
    }
}
