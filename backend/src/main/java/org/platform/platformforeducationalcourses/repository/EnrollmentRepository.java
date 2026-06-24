package org.platform.platformforeducationalcourses.repository;

import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.progress.Enrollment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

    Optional<Enrollment> findByCourseIdAndUserId(long courseId, long userId);

    List<Enrollment> findEnrollmentByUserId(long userId);

    @Query("""
            SELECT * FROM enrollments e
            JOIN courses c ON e.course_id = c.id
            JOIN modules m ON m.courseId = c.id AND m.id = :moduleId
            JOIN lessons l ON l.id = :lessonId AND l.moduleId = m.id
            WHERE e.user_id = :userId AND e.course_id = :courseId
            """)
    Optional<Enrollment> findEnrollmentForLessonIfUserIsStudying(
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
    Optional<Enrollment> findEnrollmentForTestIfUserIsStudying(
            @Param("userId") long userId,
            @Param("courseId") long courseId,
            @Param("moduleId") long moduleId,
            @Param("testId") long testId);
}
