package kira.course.adapter.out.persistence.query.learning;

import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.learning.StudentsCourseView;
import kira.course.application.port.in.query.learning.UserCourseView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LearningCourseQueryRepository {
    private final JdbcClient dbClient;

    Optional<StudentsCourseView> findStudentsCourseViewById(Long courseId) {
        return dbClient.sql("""
            SELECT id,teacher_id,tag,created_at,title,description,status
            FROM course.courses c WHERE c.id = :courseId
            """)
                .param("courseId", courseId)
                .query(StudentsCourseView.class)
                .optional();
    }

    List<UserCourseView> findEnrolledCoursesByCoursesIds(List<Long> coursesIds) {
        return dbClient.sql("""
            SELECT id,teacher_id,tag,created_at,title,status
            FROM course.courses c WHERE c.id IN :coursesIds
            """)
                .param("coursesIds", coursesIds)
                .query(UserCourseView.class)
                .list();
    }
}
