package kira.course.adapter.out.persistence.query.catalog;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.catalog.CourseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCatalogQueryRepository {
    private final JdbcClient dbClient;

    public Optional<CourseInfo> findCourseByIdAndStatus(long id, String status) {
        return dbClient.sql("""
            SELECT id,teacher_id,title,description,tag,created_at
            FROM course.courses
            WHERE status = :status AND id = :id
            """)
                .param("id", id)
                .param("status", status)
                .query(CourseInfo.class)
                .optional();
    }

    public List<CourseInfo.Module> findModulesByCourseIdAndModuleStatus(long courseId, String status) {
        return dbClient.sql("""
            SELECT id,course_id,title,description,order_index
            FROM course.modules
            WHERE status = :status AND course_id = :id
            """)
                .param("id", courseId)
                .param("status", status)
                .query(CourseInfo.Module.class)
                .list();
    }
}
