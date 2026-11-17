package kira.course.adapter.out.persistence.query.internal;

import common.domain.entity.Id;
import kira.course.api.CourseProviderQuery;
import kira.course.application.port.out.persistance.query.CourseProviderPort;
import kira.course.domain.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseInternalPersistenceAdapter implements CourseProviderPort {
    private final JdbcClient dbClient;

    @Override
    public Optional<CourseProviderQuery.CourseInfo> getCourseInfo(Id<Course> courseId) {
        return dbClient.sql("SELECT title,teacher_id FROM course.courses WHERE id = :courseId")
                .param("courseId", courseId.value())
                .query(CourseProviderQuery.CourseInfo.class)
                .optional();
    }
}
