package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.List;
import java.util.Optional;
import refactor.course.adapter.out.persistance.lesson.LessonEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DataLessonRepository extends CrudRepository<LessonEntity, Long> {

    @Query("""
            SELECT * FROM lessons l
            JOIN modules m ON l.module_id = m.id
            JOIN courses c ON m.course_id = c.id
            WHERE l.id =:id AND m.id = :moduleId AND c.id = :courseId AND c.teacher_id = :userId
            """)
    Optional<LessonEntity> findLessonIfUserIsOwner(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("id") long lessonId);

    List<LessonEntity> findAllByModuleIdIn(List<Long> moduleId);

    void deleteИн(LessonEntity entity);
}
