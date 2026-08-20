package org.platform.platformforeducationalcourses.persistance.entity.course;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refactor.course.domain.test.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "test")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestEntity {
    @Id
    private final Long id;

    private Long moduleId;
    private String description;
    private int orderIndex;

    @MappedCollection(idColumn = "test_id")
    private Set<QuestionEntity> questions;

    public static TestEntity fromTest(Test test) {
        Set<QuestionEntity> questionEntities =
                test.questions().stream().map(QuestionEntity::fromQuestion).collect(Collectors.toSet());
        return new TestEntity(
                test.getId(), test.getModuleId(), test.description(), test.getOrderIndex(), questionEntities);
    }
}
