package course.adapter.out.persistance.query.owned;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import course.adapter.out.persistance.module.ModuleEntity;
import course.application.port.in.query.owned.TeacherCourseView;
import course.application.port.in.query.owned.TeachersModuleView;

import java.util.List;
import java.util.Optional;

public interface OwnedModuleQueryRepository extends CrudRepository<ModuleEntity, Long> {

    @Query("""
            SELECT m.id, m.course_id,m.title,m.description,m.orderIndex, c.teacher_id
            FROM modules m
            JOIN courses c ON m.course_id = c.id
            WHERE m.id = :id
            """)
    Optional<TeachersModuleView> findTeacherViewById(@Param("id") Long id);

    @Query("SELECT id,orderIndex,title,description FROM modules m WHERE m.course_id = :courseId")
    List<TeacherCourseView.Module> findTeacherCourseModuleViewByCourseId(@Param("courseId") Long courseId);
}
