package refactor.course.adapter.out.persistance.module;

import java.util.Collections;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.platform.platformforeducationalcourses.persistance.entity.reference.LessonRef;
import org.platform.platformforeducationalcourses.persistance.entity.reference.TestRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules")
@Getter
@AllArgsConstructor
public class ModuleEntity {
    @Id
    private final Long id;

    private final Long courseId;
    private String title;
    private String description;
    private int orderIndex;

    @MappedCollection(idColumn = "module_id")
    Set<LessonRef> lessons;

    @MappedCollection(idColumn = "module_id")
    Set<TestRef> autoTests;

    public Set<LessonRef> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    public Set<TestRef> getAutoTests() {
        return Collections.unmodifiableSet(autoTests);
    }

}
