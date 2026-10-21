package kira.course.adapter.out.persistence.course;

import java.time.LocalDateTime;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "courses", schema = "course")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class CourseEntity {
    @Id
    private final Long id;

    private final Long teacherId;
    private final String title;
    private final String description;
    private final Tag tag;
    private final LocalDateTime createdAt;
    private final CourseStatus status;
}
