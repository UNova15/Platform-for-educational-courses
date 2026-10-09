package kira.course.adapter.out.persistence.query.owned;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kira.course.adapter.out.persistence.test.TestEntity;
import kira.course.application.port.in.query.owned.FullTestView;
import kira.course.application.port.in.query.owned.TeachersModuleView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OwnedTestQueryRepository extends CrudRepository<TestEntity, Long> {

    @Query("SELECT t.id,t.title,t.status FROM course.tests t WHERE t.module_id = :moduleId ")
    List<TeachersModuleView.Test> findTeacherModuleTestViewByModuleId(@Param("moduleId") Long moduleId);

    @Query("""
            SELECT t.id,t.moduleId,t.title,t.description,t.order_index,t.status,c.teacher_id
            FROM course.tests t
            JOIN course.modules m ON m.id = t.module_id
            JOIN course.courses c ON c.id = m.course_id
            WHERE t.id = :id
            """)
    Optional<FullTestView> findTeachersTestViewById(@Param("id") Long id);

    @Query("SELECT q.id,q.test_id,q.question,q.order_index FROM course.questions q WHERE q.test_id = :id")
    List<FullTestView.Question> findAllQuestionsInTestByTestId(@Param("id") Long id);

    @Query("SELECT o.id,o.question_id,o.option,o.is_correct FROM course.options o WHERE o.question_id IN :ids")
    List<FullTestView.Option> findAllOptionsInTestByQuestionIds(@Param("ids") Collection<Long> ids);
}
