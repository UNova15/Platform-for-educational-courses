package kira.course.adapter.out.persistence.query.learning;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.learning.StudentsCourseView;
import kira.course.application.port.in.query.learning.StudentsModuleView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LearningModuleQueryRepository {
    private final JdbcClient dbClient;

    List<StudentsCourseView.Module> findStudentsCourseModuleViewByCourseId(Long courseId, String status) {
        return dbClient.sql("""
            SELECT title,id,order_index FROM course.modules m
            WHERE m.course_id = :courseId
            AND m.status = :status
            """)
                .param("courseId", courseId)
                .param("status", status)
                .query(StudentsCourseView.Module.class)
                .list();
    }

    Optional<StudentsModuleView> findStudentsModuleView(Long moduleId, String status) {
        return dbClient.sql("""
            SELECT id,course_id,order_index,title,description
            FROM course.modules m
            WHERE m.id = :moduleId
            AND m.status = :status
            """)
                .param("moduleId", moduleId)
                .param("status", status)
                .query(StudentsModuleView.class)
                .optional();
    }
}
