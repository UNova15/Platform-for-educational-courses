package kira.course.adapter.out.persistence.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "course.modules")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class ModuleEntity {
    @Id
    private final Long id;

    private final Long courseId;
    private final String title;
    private final String description;
    private final int orderIndex;
}
