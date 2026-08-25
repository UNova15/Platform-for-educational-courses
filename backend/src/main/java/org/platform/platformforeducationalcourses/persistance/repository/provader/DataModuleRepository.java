package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.List;
import java.util.Optional;
import refactor.course.adapter.out.persistance.module.ModuleEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DataModuleRepository extends CrudRepository<ModuleEntity, Long> {
    @Query("""
            SELECT * FROM modules m
            JOIN courses c ON m.course_id = c.id
            WHERE m.id = :moduleId AND m.course_id = :courseId AND c.teacher_id = :userId
            """)
    Optional<ModuleEntity> findModuleIfUserIsOwner(
            @Param("userId") long userId, @Param("courseId") long courseId, @Param("moduleId") long moduleId);

    List<ModuleEntity> findAllByCourseId(long courseId);
}
