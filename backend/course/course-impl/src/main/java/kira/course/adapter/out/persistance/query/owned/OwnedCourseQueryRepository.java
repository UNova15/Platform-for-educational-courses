package kira.course.adapter.out.persistance.query.owned;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import kira.course.adapter.out.persistance.course.CourseEntity;
import kira.course.application.port.in.query.owned.OwnedCoursesView;
import kira.course.application.port.in.query.owned.TeacherCourseView;

import java.util.List;
import java.util.Optional;

public interface OwnedCourseQueryRepository extends CrudRepository<CourseEntity, Long> {

    @Query("SELECT id,title,description,tag,createdAt FROM  courses c WHERE c.teacher_id = :teacherId ")
    List<OwnedCoursesView> findOwnedCoursesViewByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT id,teacher_id,tag,created_at,title,description FROM courses c WHERE c.course_id = :courseId ")
    Optional<TeacherCourseView> findTeacherCourseViewById(@Param("courseId") Long courseId);
}
