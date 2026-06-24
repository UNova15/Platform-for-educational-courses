package org.platform.platformforeducationalcourses.repository.course;

import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ModuleRepository extends CrudRepository<CourseModule, Long> {
    @Query("""
            SELECT * FROM modules m
            JOIN courses c ON m.course_id = c.id
            WHERE m.id = :moduleId AND m.course_id = :courseId AND c.teacher_id = :userId
            """)
    Optional<CourseModule> findModuleIfUserIsOwner(
            @Param("userId") long userId, @Param("courseId") long courseId, @Param("moduleId") long moduleId);

    List<CourseModule> findAllByCourseId(long courseId);
}
