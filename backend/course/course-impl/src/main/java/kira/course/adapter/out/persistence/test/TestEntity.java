package kira.course.adapter.out.persistence.test;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tests")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class TestEntity {
    @Id
    private final Long id;

    private Long moduleId;
    private String title;
    private String description;
    private int orderIndex;


    @MappedCollection(idColumn = "test_id")
    private Set<QuestionEntity> questions;
}
