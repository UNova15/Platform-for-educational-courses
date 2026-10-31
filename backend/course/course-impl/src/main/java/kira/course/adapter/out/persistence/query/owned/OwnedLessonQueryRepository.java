package kira.course.adapter.out.persistence.query.owned;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.OwnedModuleView;
import kira.course.application.port.in.query.owned.TeachersLessonView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedLessonQueryRepository {
    private final JdbcClient dbClient;

    List<OwnedModuleView.Lesson> findAllTeacherModuleLessonViewByModuleId(Long moduleId) {
        return dbClient.sql("""
            SELECT l.id,l.title,l.status
            FROM course.lessons l
            WHERE l.module_id = :moduleId
            """)
                .param("moduleId", moduleId)
                .query(OwnedModuleView.Lesson.class)
                .list();
    }

    Optional<TeachersLessonView> findTeachersLessonViewById(Long id) {
        return dbClient.sql("""
                        SELECT l.id,l.module_id,l.title,l.type,l.content,l.order_index,l.mandatory,l.status,c.teacher_id
                        FROM course.lessons l
                        JOIN course.modules m ON l.module_id = m.id
                        JOIN course.courses c ON m.course_id = c.id
                        WHERE l.id = :lessonId
                        """)
                .param("lessonId", id)
                .query(TeachersLessonView.class)
                .optional();
    }
}
