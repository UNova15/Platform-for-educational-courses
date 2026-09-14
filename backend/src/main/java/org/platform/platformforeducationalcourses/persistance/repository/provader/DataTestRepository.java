package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.List;
import java.util.Optional;
import refactor.course.implementation.adapter.out.persistance.test.TestEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DataTestRepository extends CrudRepository<TestEntity, Long> {

    List<TestEntity> findAllByModuleIdIn(List<Long> moduleId);

    @Query("""
            SELECT * FROM test t
            JOIN modules m ON t.module_id = m.id
            JOIN courses c ON m.course_id = c.id
            WHERE t.id = :testId AND m.id = :moduleId AND c.id = :courseId AND c.user_id = :userId
            """)
    Optional<TestEntity> findTestIfUserIsOwner(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("testId") long testId);
}
