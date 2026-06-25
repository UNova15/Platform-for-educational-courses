package org.platform.platformforeducationalcourses.repository.course;

import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TestRepository extends CrudRepository<Test, Long> {

    List<Test> findAllByModuleIdIn(List<Long> moduleId);

    @Query("""
            SELECT * FROM test t
            JOIN modules m ON t.module_id = m.id
            JOIN courses c ON m.course_id = c.id
            WHERE t.id = :testId AND m.id = :moduleId AND c.id = :courseId AND c.user_id = :userId
            """)
    Optional<Test> findTestIfUserIsOwner(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("testId") long testId);
}
