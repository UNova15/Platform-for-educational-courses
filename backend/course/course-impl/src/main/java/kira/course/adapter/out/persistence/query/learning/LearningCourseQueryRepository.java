package kira.course.adapter.out.persistence.query.learning;

import java.util.List;
import java.util.Optional;
import kira.course.adapter.out.persistence.course.CourseEntity;
import kira.course.application.port.in.query.learning.StudentsCourseView;
import kira.course.application.port.in.query.learning.UserCourseView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LearningCourseQueryRepository extends CrudRepository<CourseEntity, Long> {

    @Query("SELECT id,teacher_id,tag,created_at,title,description FROM course.courses c WHERE c.id = :courseId")
    Optional<StudentsCourseView> findStudentsCourseViewById(@Param("courseId") Long courseId);

    @Query("SELECT id,teacher_id,tag,created_at,title FROM course.courses c WHERE c.id IN :coursesIds")
    List<UserCourseView> findEnrolledCoursesByCoursesIds(@Param("coursesIds") List<Long> coursesIds);
}
