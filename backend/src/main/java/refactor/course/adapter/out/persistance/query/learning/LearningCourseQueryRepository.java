package refactor.course.adapter.out.persistance.query.learning;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import refactor.course.adapter.out.persistance.course.CourseEntity;
import refactor.course.application.port.in.query.learning.EnrolledCourse;
import refactor.course.application.port.in.query.learning.StudentsCourseView;

import java.util.List;
import java.util.Optional;

public interface LearningCourseQueryRepository extends CrudRepository<CourseEntity, Long> {

    @Query("SELECT id,teacher_id,tag,created_at,title,description FROM courses c WHERE c.id = :courseId")
    Optional<StudentsCourseView> findStudentsCourseViewById(@Param("courseId") Long courseId);

    @Query("SELECT id,teacher_id,tag,created_at,title FROM courses c WHERE c.id IN :coursesIds")
    List<EnrolledCourse> findEnrolledCoursesByCoursesIds(@Param("coursesIds") List<Long> coursesIds);
}
