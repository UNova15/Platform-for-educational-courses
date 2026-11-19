package kira.course.adapter.out.persistence.query.owned;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.OwnedModuleView;
import kira.course.application.port.in.query.owned.OwnedTestView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedTestQueryRepository {
    private final JdbcClient dbClient;

    List<OwnedModuleView.Test> findTeacherModuleTestViewByModuleId(Long moduleId) {
        return dbClient.sql("""
            SELECT t.id,t.title,t.status
            FROM course.tests t
            WHERE t.module_id = :moduleId
            """)
                .param("moduleId", moduleId)
                .query(OwnedModuleView.Test.class)
                .list();
    }

    Optional<OwnedTestView> findTeachersTestViewById(Long id) {
        return dbClient.sql("""
            SELECT t.id,t.module_id,t.title,t.description,t.order_index,t.status,c.teacher_id
            FROM course.tests t
            JOIN course.modules m ON m.id = t.module_id
            JOIN course.courses c ON c.id = m.course_id
            WHERE t.id = :id
            """).param("id", id).query(OwnedTestView.class).optional();
    }

    List<OwnedTestView.Question> findAllQuestionsInTestByTestId(Long id) {
        return dbClient.sql("""
            SELECT q.id,q.test_id,q.question,q.order_index
            FROM course.test_questions q
            WHERE q.test_id = :id
            """)
                .param("id", id)
                .query(OwnedTestView.Question.class)
                .list();
    }

    List<OwnedTestView.Option> findAllOptionsInTestByQuestionIds(Collection<Long> ids) {
        return dbClient.sql("""
                SELECT o.id,o.question_id,o.option,o.is_correct
                FROM course.question_options o
                WHERE o.question_id IN (:ids)
                """)
                .param("ids", ids)
                .query(OwnedTestView.Option.class)
                .list();
    }
}
