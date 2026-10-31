package kira.course.adapter.out.persistence.query.learning;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.learning.StudentsModuleView;
import kira.course.application.port.in.query.learning.StudentsTestView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LearningTestQueryRepository {
    private final JdbcClient dbClient;

    List<StudentsModuleView.Test> findStudentsModuleTestView(Long moduleId, String status) {
        return dbClient.sql("""
            SELECT id,title,module_id,order_index
            FROM course.tests t
            WHERE t.module_id = :moduleId
            AND t.status = :status
            """)
                .param("moduleId", moduleId)
                .param("status", status)
                .query(StudentsModuleView.Test.class)
                .list();
    }

    Optional<StudentsTestView> findStudentsTestView(Long testId, String status) {
        return dbClient.sql("""
            SELECT t.id,t.module_id,m.course_id,t.order_index,t.title,t.description
            FROM course.tests t
            JOIN course.modules m ON t.module_id = m.id
            WHERE t.id = :testId
            AND t.status = :status
            """)
                .param("testId", testId)
                .param("status", status)
                .query(StudentsTestView.class)
                .optional();
    }

    List<StudentsTestView.Question> findStudentsTestQuestionsView(Long testId) {
        return dbClient.sql("""
            SELECT q.id,q.test_id,q.order_index,q.question
            FROM course.test_questions q
            WHERE  q.test_id = :testId
            """)
                .param("testId", testId)
                .query(StudentsTestView.Question.class)
                .list();
    }

    List<StudentsTestView.AnswerOption> findStudentsTestOptionsView(Collection<Long> questionId) {
        return dbClient.sql("""
            SELECT id,question_id,option
            FROM course.question_options o
            WHERE o.question_id IN (:questionsIds)
            """)
                .param("questionsIds", questionId)
                .query(StudentsTestView.AnswerOption.class)
                .list();
    }
}
