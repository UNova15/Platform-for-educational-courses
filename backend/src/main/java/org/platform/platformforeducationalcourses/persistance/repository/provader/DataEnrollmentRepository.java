package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.persistance.entity.progress.EnrollmentEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DataEnrollmentRepository extends CrudRepository<EnrollmentEntity, Long> {

    Optional<EnrollmentEntity> findByCourseIdAndUserId(long courseId, long userId);

    List<EnrollmentEntity> findEnrollmentByUserId(long userId);

    @Query("""
            SELECT * FROM enrollments e
            JOIN courses c ON e.course_id = c.id
            JOIN modules m ON m.courseId = c.id AND m.id = :moduleId
            JOIN lessons l ON l.id = :lessonId AND l.moduleId = m.id
            WHERE e.user_id = :userId AND e.course_id = :courseId
            """)
    Optional<EnrollmentEntity> findEnrollmentForLessonIfUserIsStudying(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("lessonId") long lessonId);

    @Query("""
            SELECT * FROM enrollments e
            JOIN courses c ON e.course_id = c.id
            JOIN modules m ON m.courseId = c.id AND m.id = :moduleId
            JOIN test t ON t.id = :testId AND t.moduleId = m.id
            WHERE e.user_id = :userId AND e.course_id = :courseId
            """)
    Optional<EnrollmentEntity> findEnrollmentForTestIfUserIsStudying(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("testId") long testId);
}
