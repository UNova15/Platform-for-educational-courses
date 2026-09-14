package course.adapter.out.persistance.query.owned;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import course.adapter.out.persistance.lesson.LessonEntity;
import course.application.port.in.query.owned.TeachersLessonView;
import course.application.port.in.query.owned.TeachersModuleView;

import java.util.List;
import java.util.Optional;

public interface OwnedLessonQueryRepository extends CrudRepository<LessonEntity, Long> {

    @Query("SELECT id,title FROM lessons l WHERE l.module_id = :moduleId ")
    List<TeachersModuleView.Lesson> findAllTeacherModuleLessonViewByModuleId(@Param("moduleId") Long moduleId);

    @Query("""
            SELECT l.id,l.module_id,l.title,l.type,l.content,l.orderIndex,l.mandatory,c.teacher_id
            FROM lessons l
            JOIN modules m ON l.module_id = m.id
            JOIN courses c ON l.course_id = c.id
            WHERE l.id = :lessonId
            """)
    Optional<TeachersLessonView> findTeachersLessonViewById(@Param("lessonId") Long id);
}
