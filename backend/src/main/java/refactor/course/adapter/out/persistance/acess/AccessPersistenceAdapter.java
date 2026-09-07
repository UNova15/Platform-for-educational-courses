package refactor.course.adapter.out.persistance.acess;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import refactor.common.domain.Id;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.user.Account;

@Repository
@RequiredArgsConstructor
public class AccessPersistenceAdapter implements CourseAccessPort {
    private final JdbcClient dbClient;

    @Override
    public boolean isLessonOwner(Id<Account> requesterId, Id<Lesson> lessonId) {
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
    public boolean isModuleOwner(Id<Account> requesterId, Id<CourseModule> moduleId) {
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
    public boolean isCourseOwner(Id<Account> requesterId, Id<Course> courseId) {
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
    public boolean isTestOwner(Id<Account> requesterId, Id<Test> testId) {
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
