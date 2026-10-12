package kira.course.adapter.out.persistence.module;

import kira.course.domain.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules", schema = "course")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class ModuleEntity {
    @Id
    private final Long id;

    private final Long courseId;
    private final String title;
    private final String description;
    private final int orderIndex;
    private final Status status;
}
