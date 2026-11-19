package kira.course.adapter.out.persistence.lesson;

import java.util.Optional;
import kira.course.domain.shared.CourseContentStatus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface DataLessonRepository extends CrudRepository<LessonEntity, Long> {
    boolean existsByModuleId(Long moduleId);

    @Modifying
    @Query("""
        UPDATE course.lessons  SET status = :status
        WHERE module_id  IN (
            SELECT id FROM course.modules WHERE course_id = :courseId
        )
        """)
    void updateAllStatusesByCourseId(Long courseId, String status);

    @Modifying
    @Query("UPDATE course.lessons SET status = :status WHERE module_id = :moduleId")
    void updateAllStatusesByModuleId(Long moduleId, String status);

    @Query("SELECT status FROM course.lessons WHERE id = :Id")
    Optional<CourseContentStatus> findStatusById(@Param("Id") Long id);
}
