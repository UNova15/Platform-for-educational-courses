package kira.course.adapter.out.persistence.test;

import java.util.Collections;
import java.util.Set;

import kira.course.domain.shared.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tests",schema = "course")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class TestEntity {
    @Id
    private final Long id;

    private final Long moduleId;
    private final String title;
    private final String description;
    private final int orderIndex;
    private final Status status;

    @Getter(AccessLevel.NONE)
    @MappedCollection(idColumn = "test_id")
    private final Set<QuestionEntity> questions;

    public Set<QuestionEntity> questions() {
        return Collections.unmodifiableSet(questions);
    }
}
