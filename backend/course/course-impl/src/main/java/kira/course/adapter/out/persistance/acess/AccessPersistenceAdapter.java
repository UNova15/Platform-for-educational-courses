package kira.course.adapter.out.persistance.acess;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import kira.course.application.port.out.persistance.access.CourseAccessPort;

@Repository
@RequiredArgsConstructor
public class AccessPersistenceAdapter implements CourseAccessPort {
    private final JdbcClient dbClient;

    @Override
    public boolean isLessonOwner(Id<User> requesterId, Id<Lesson> lessonId) {
        return dbClient.sql("""
            SELECT EXISTS(SELECT 1 FROM courses c
            JOIN modules m ON m.course_id = c.id
            JOIN lessons l ON l.module_id = m.id
            WHERE l.id = :lessonId AND c.teacher_id = :requesterId)
            """)
                .param("lessonId", lessonId.value())
                .param("requesterId", requesterId.value())
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean isModuleOwner(Id<User> requesterId, Id<CourseModule> moduleId) {
        return dbClient.sql("""
            SELECT EXISTS(SELECT 1 FROM courses c
            JOIN modules m ON c.id = m.course_id
            WHERE m.id = :moduleId AND c.teacher_id = :requesterId)
            """)
                .param("moduleId", moduleId.value())
                .param("requesterId", requesterId.value())
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean isCourseOwner(Id<User> requesterId, Id<Course> courseId) {
        return dbClient.sql("""
            SELECT EXISTS(SELECT 1 FROM courses c
            WHERE c.id = :courseId AND c.teacher_id = :requesterId)
            """)
                .param("courseId", courseId.value())
                .param("requesterId", requesterId.value())
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean isTestOwner(Id<User> requesterId, Id<Test> testId) {
        return dbClient.sql("""
            SELECT EXISTS(SELECT 1 FROM courses c
            JOIN modules m ON m.course_id = c.id
            JOIN tests t ON t.module_id = m.id
            WHERE t.id = :testId AND c.teacher_id = :requesterId)
            """)
                .param("testId", testId.value())
                .param("requesterId", requesterId.value())
                .query(Boolean.class)
                .single();
    }
}
