package org.platform.platformforeducationalcourses.domain.course;

import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "test")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Test {
    @Id
    private final Long id;

    private Long moduleId;
    private String description;
    private int orderIndex;

    @MappedCollection(idColumn = "test_id")
    private Set<Question> questions;

    public Set<Question> getQuestions() {
        return Collections.unmodifiableSet(questions);
    }

    public static Test createNew(long moduleId, String description, int orderIndex, Set<Question> questions) {
        if (moduleId < 0 || orderIndex < 0 || questions.isEmpty()) {
            throw new IllegalArgumentException("Incorrect data to create test");
        }
        return new Test(null, moduleId, description, orderIndex, questions);
    }

    public void update(String description, int orderIndex, Set<Question> questions) {
        if (description == null
                || description.isBlank()
                || orderIndex < 0
                || questions == null
                || questions.isEmpty()) {
            throw new IllegalArgumentException("Incorrect data to update test");
        }

        this.description = description;
        this.orderIndex = orderIndex;
        this.questions = questions;
    }
}
