package kira.course.adapter.out.persistence.module;

import kira.course.domain.shared.CourseContentStatus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

interface DataModuleRepository extends CrudRepository<ModuleEntity, Long> {
    boolean existsByCourseId(Long courseId);

    @Modifying
    @Query("UPDATE course.modules SET status = :status WHERE course_id = :courseId")
    void updateAllStatusesByCourseId(Long courseId, String status);

    @Query("""
            SELECT status FROM course.modules 
            WHERE id = (
                SELECT module_id FROM course.lessons l 
                WHERE l.id = :lessonId
            )
            """)
    CourseContentStatus findStatusByLessonId(Long lessonId);

    @Query("""
            SELECT status FROM course.modules 
            WHERE id = (
                SELECT module_id FROM course.tests t 
                WHERE t.id = :testId
            )
            """)
    CourseContentStatus findStatusByTestId(Long testId);
}
