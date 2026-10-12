package kira.course.adapter.out.persistence.query.owned;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.TeacherCourseView;
import kira.course.application.port.in.query.owned.TeachersModuleView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedModuleQueryRepository {
    private final JdbcClient dbClient;

    Optional<TeachersModuleView> findTeacherViewById(Long id) {
        return dbClient.sql("""
            SELECT m.id, m.course_id,m.title,m.description,m.orderIndex,m.status, c.teacher_id
            FROM course.modules m
            JOIN course.courses c ON m.course_id = c.id
            WHERE m.id = :id
            """).param("id", id).query(TeachersModuleView.class).optional();
    }

    List<TeacherCourseView.Module> findTeacherCourseModuleViewByCourseId(Long courseId) {
        return dbClient.sql("""
            SELECT m.id,m.orderIndex,m.title,m.description,m.status
            FROM course.modules m
            WHERE m.course_id =:courseId
            """)
                .param("courseId", courseId)
                .query(TeacherCourseView.Module.class)
                .list();
    }
}
