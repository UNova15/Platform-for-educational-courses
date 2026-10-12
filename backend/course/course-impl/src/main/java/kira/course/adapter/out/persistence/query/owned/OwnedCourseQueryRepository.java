package kira.course.adapter.out.persistence.query.owned;

import java.util.List;
import java.util.Optional;
import kira.course.adapter.out.persistence.course.CourseEntity;
import kira.course.application.port.in.query.owned.OwnedCoursesView;
import kira.course.application.port.in.query.owned.TeacherCourseView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface OwnedCourseQueryRepository extends CrudRepository<CourseEntity, Long> {

    @Query("SELECT c.id,c.title,c.description,c.tag,c.created_at,c.status FROM  course.courses c WHERE c.teacher_id = :teacherId ")
    List<OwnedCoursesView> findOwnedCoursesViewByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT c.id,c.teacher_id,c.tag,c.created_at,c.title,c.description,c.status FROM course.courses c WHERE c.course_id = :courseId ")
    Optional<TeacherCourseView> findTeacherCourseViewById(@Param("courseId") Long courseId);
}
