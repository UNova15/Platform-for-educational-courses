package kira.course.adapter.out.persistence.query.learning;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.learning.StudentsLessonView;
import kira.course.application.port.in.query.learning.StudentsModuleView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LearningLessonQueryRepository {
    private final JdbcClient dbClient;

    List<StudentsModuleView.Lesson> findStudentsModuleLessonView(Long moduleId, String status) {
        return dbClient.sql("""
            SELECT id,title,module_id,order_index,mandatory
            FROM course.lessons l
            WHERE l.module_id = :moduleId
            AND l.status = :status
            """)
                .param("moduleId", moduleId)
                .param("status", status)
                .query(StudentsModuleView.Lesson.class)
                .list();
    }

    Optional<StudentsLessonView> findStudentsLessonView(Long lessonId, String status) {
        return dbClient.sql("""
            SELECT l.id,l.module_id,m.course_id,l.order_index,l.mandatory,l.title,l.type,l.content
            FROM course.lessons l
            JOIN course.modules m ON l.module_id = m.id
            WHERE l.id = :lessonId
            AND l.status = :status
            """)
                .param("lessonId", lessonId)
                .param("status", status)
                .query(StudentsLessonView.class)
                .optional();
    }
}
