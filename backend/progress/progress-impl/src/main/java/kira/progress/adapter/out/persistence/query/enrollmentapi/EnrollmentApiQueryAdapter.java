package kira.progress.adapter.out.persistence.query.enrollmentapi;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.application.port.out.persistance.query.EnrollmentApiQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EnrollmentApiQueryAdapter implements EnrollmentApiQueryPort {
    private final JdbcClient dbClient;

    @Override
    public List<Id<User>> findEnrolledUsersByCourseId(Id<Course> courseId) {
        List<Long> ids = dbClient.sql("SELECT e.user_id FROM progress.enrollments e WHERE e.course_id = :courseId")
                .param("courseId", courseId.value())
                .query(Long.class)
                .list();

        return ids.stream().map(Id::<User>of).toList();
    }

    @Override
    public List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId) {
        List<Long> ids = dbClient.sql("SELECT e.course_id FROM progress.enrollments e WHERE e.user_id = :userId")
                .param("userId", userId.value())
                .query(Long.class)
                .list();

        return ids.stream().map(Id::<Course>of).toList();
    }

    @Override
    public boolean isEnrollmentExist(Id<User> studentId, Id<Course> courseId) {
        return dbClient.sql(
                        " SELECT EXISTS(SELECT 1 FROM progress.enrollments WHERE course_id = :courseId AND user_id ="
                                + " :userId)")
                .param("courseId", courseId.value())
                .param("userId", studentId.value())
                .query(Boolean.class)
                .single();
    }
}
