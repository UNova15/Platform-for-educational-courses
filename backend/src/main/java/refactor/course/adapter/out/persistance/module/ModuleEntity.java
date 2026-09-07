package refactor.course.adapter.out.persistance.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class ModuleEntity {
    @Id
    private final Long id;

    private final Long courseId;
    private String title;
    private String description;
    private int orderIndex;
}
