package org.platform.platformforeducationalcourses.repository.course;

import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

    @Query("""
            SELECT * FROM lessons l
            JOIN modules m ON l.module_id = m.id 
            JOIN courses c ON m.course_id = c.id
            WHERE l.id =:id AND m.id = :moduleId AND c.id = :courseId AND c.teacher_id = :userId
            """)
    Optional<Lesson> findLessonIfUserIsOwner(@Param("userId") long userId,
                                             @Param("courseId") long courseId,
                                             @Param("moduleId") long moduleId,
                                             @Param("id") long lessonId);

    List<Lesson> findAllByModuleIdIn(List<Long> moduleId);
}
