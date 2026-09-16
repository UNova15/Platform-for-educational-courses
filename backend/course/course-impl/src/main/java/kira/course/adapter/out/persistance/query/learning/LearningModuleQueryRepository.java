package kira.course.adapter.out.persistance.query.learning;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import kira.course.adapter.out.persistance.module.ModuleEntity;
import kira.course.application.port.in.query.learning.StudentsCourseView;
import kira.course.application.port.in.query.learning.StudentsModuleView;

import java.util.List;
import java.util.Optional;

public interface LearningModuleQueryRepository extends CrudRepository<ModuleEntity, Long> {

    @Query("SELECT title,id,order_index FROM modules m WHERE m.course_id = :courseId ")
    List<StudentsCourseView.Module> findStudentsCourseModuleViewByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT id,course_id,order_index,title,description FROM modules m WHERE m.id = :moduleId")
    Optional<StudentsModuleView> findStudentsModuleView(@Param("moduleId") Long moduleId);
}
