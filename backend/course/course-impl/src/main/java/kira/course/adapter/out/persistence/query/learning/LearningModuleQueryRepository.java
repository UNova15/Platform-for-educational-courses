package kira.course.adapter.out.persistence.query.learning;

import java.util.List;
import java.util.Optional;
import kira.course.adapter.out.persistence.module.ModuleEntity;
import kira.course.application.port.in.query.learning.StudentsCourseView;
import kira.course.application.port.in.query.learning.StudentsModuleView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//TODO можно ли делать публичные Entity и использовать их здесь?
interface LearningModuleQueryRepository extends CrudRepository<ModuleEntity, Long> {

    //TODO насколько правильно хардкодить статус?
    @Query("SELECT title,id,order_index FROM course.modules m WHERE m.course_id = :courseId AND m.status = 'PUBLISHED'")
    List<StudentsCourseView.Module> findStudentsCourseModuleViewByCourseId(@Param("courseId") Long courseId);

    @Query("""
            SELECT id,course_id,order_index,title,description
            FROM course.modules m
            WHERE m.id = :moduleId
            AND m.status = 'PUBLISHED'
            """)
    Optional<StudentsModuleView> findStudentsModuleView(@Param("moduleId") Long moduleId);
}
