package kira.course.adapter.out.persistence.course;

import java.util.Optional;
import kira.course.domain.course.CourseStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface DataCourseRepository extends CrudRepository<CourseEntity, Long> {

    @Query("""
        SELECT status FROM course.courses
        WHERE id = (
            SELECT course_id FROM course.modules
            WHERE id = :moduleId
        )
        """)
    CourseStatus findCourseStatusByModuleId(Long moduleId);

    @Query("""
        SELECT status FROM course.courses
        WHERE id = (
            SELECT m.course_id FROM course.modules m
            JOIN course.lessons l ON l.module_id = m.id
            WHERE l.id = :lessonId
        )
        """)
    CourseStatus findCourseStatusByLessonId(Long lessonId);

    @Query("""
        SELECT status FROM course.courses
        WHERE id = (
            SELECT m.course_id FROM course.modules m
            JOIN course.tests t ON t.module_id = m.id
            WHERE t.id = :testId
        )
        """)
    CourseStatus findCourseStatusByTestId(Long testId);

    @Query("SELECT status FROM course.courses WHERE id = :id")
    Optional<CourseStatus> findStatusById(@Param("id") long id);
}
