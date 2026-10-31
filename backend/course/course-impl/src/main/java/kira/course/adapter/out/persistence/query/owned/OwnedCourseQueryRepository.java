package kira.course.adapter.out.persistence.query.owned;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.OwnedCoursesView;
import kira.course.application.port.in.query.owned.TeacherCourseView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedCourseQueryRepository {
    private final JdbcClient dbClient;

    List<OwnedCoursesView> findOwnedCoursesViewByTeacherId(Long teacherId) {
        return dbClient.sql("""
            SELECT c.id,c.title,c.description,c.tag,c.created_at,c.status
            FROM course.courses c
            WHERE c.teacher_id = :teacherId
            """)
                .param("teacherId", teacherId)
                .query(OwnedCoursesView.class)
                .list();
    }

    Optional<TeacherCourseView> findOwnedCourseViewById(Long courseId) {
        return dbClient.sql("""
            SELECT c.id,c.teacher_id,c.tag,c.created_at,c.title,c.description,c.status
            FROM course.courses c
            WHERE c.id = :courseId
            """)
                .param("courseId", courseId)
                .query(TeacherCourseView.class)
                .optional();
    }
}
