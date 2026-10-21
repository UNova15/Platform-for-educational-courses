package kira.course.adapter.out.persistence.query.owned;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.FullTestView;
import kira.course.application.port.in.query.owned.TeachersModuleView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedTestQueryRepository {
    private final JdbcClient dbClient;

    List<TeachersModuleView.Test> findTeacherModuleTestViewByModuleId(Long moduleId) {
        return dbClient.sql("""
            SELECT t.id,t.title,t.status
            FROM course.tests t
            WHERE t.module_id = :moduleId
            """)
                .param("moduleId", moduleId)
                .query(TeachersModuleView.Test.class)
                .list();
    }

    Optional<FullTestView> findTeachersTestViewById(Long id) {
        return dbClient.sql("""
            SELECT t.id,t.moduleId,t.title,t.description,t.order_index,t.status,c.teacher_id
            FROM course.tests t
            JOIN course.modules m ON m.id = t.module_id
            JOIN course.courses c ON c.id = m.course_id
            WHERE t.id = :id
            """).param("id", id).query(FullTestView.class).optional();
    }

    List<FullTestView.Question> findAllQuestionsInTestByTestId(Long id) {
        return dbClient.sql("""
            SELECT q.id,q.test_id,q.question,q.order_index
            FROM course.questions q
            WHERE q.test_id = :id
            """)
                .param("id", id)
                .query(FullTestView.Question.class)
                .list();
    }

    List<FullTestView.Option> findAllOptionsInTestByQuestionIds(Collection<Long> ids) {
        return dbClient.sql("""
                SELECT o.id,o.question_id,o.option,o.is_correct
                FROM course.options o
                WHERE o.question_id IN :ids
                """)
                .param("ids", ids)
                .query(FullTestView.Option.class)
                .list();
    }
}
