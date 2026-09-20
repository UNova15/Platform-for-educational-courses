package kira.course.adapter.out.persistence.query.learning;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import kira.course.adapter.out.persistence.lesson.LessonEntity;
import kira.course.application.port.in.query.learning.StudentsLessonView;
import kira.course.application.port.in.query.learning.StudentsModuleView;

import java.util.List;
import java.util.Optional;

public interface LearningLessonQueryRepository extends CrudRepository<LessonEntity, Long> {

    @Query("SELECT id,title,module_id,order_index,mandatory FROM course.lessons l WHERE l.module_id = :moduleId")
    List<StudentsModuleView.Lesson> findStudentsModuleLessonView(@Param("moduleId") Long moduleId);

    @Query("""
            SELECT l.id,l.module_id,m.course_id,l.order_index,l.mandatory,l.title,l.type,l.content
            FROM course.lessons l
            JOIN course.modules m ON l.module_id = m.id
            WHERE l.id = :lessonId
            """)
    Optional<StudentsLessonView> findStudentsLessonView(@Param("lessonId") Long lessonId);
}
