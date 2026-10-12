package kira.course.adapter.out.persistence.test;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

interface DataTestRepository extends CrudRepository<TestEntity, Long> {
    boolean existsByModuleId(Long moduleId);

    @Modifying
    @Query("""
        UPDATE course.tests SET status = :status
        WHERE module_id IN (
            SELECT id FROM course.modules WHERE course_id = :courseId
        )
        """)
    void updateAllStatusesByCourseId(Long courseId, String status);

    @Modifying
    @Query("UPDATE course.tests SET status = :status WHERE module_id = :moduleId")
    void updateAllStatusesByModuleId(Long moduleId, String status);
}
