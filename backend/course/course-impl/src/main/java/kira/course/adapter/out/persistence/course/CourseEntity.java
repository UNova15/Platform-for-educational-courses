package kira.course.adapter.out.persistence.course;

import java.time.LocalDateTime;

import kira.course.domain.course.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "courses")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class CourseEntity {
    @Id
    private final Long id;

    private Long teacherId;
    private String title;
    private String description;
    private Tag tag;
    private LocalDateTime createdAt;
}
