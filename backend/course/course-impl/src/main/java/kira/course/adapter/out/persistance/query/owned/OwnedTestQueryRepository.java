package kira.course.adapter.out.persistance.query.owned;

import kira.course.adapter.out.persistance.test.TestEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import kira.course.application.port.in.query.owned.TeachersModuleView;
import kira.course.application.port.in.query.owned.FullTestView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OwnedTestQueryRepository extends CrudRepository<TestEntity, Long> {

    @Query("SELECT id,title FROM tests t WHERE t.module_id = :moduleId ")
    List<TeachersModuleView.Test> findTeacherModuleTestViewByModuleId(@Param("moduleId") Long moduleId);

    @Query("""
            SELECT t.id,t.moduleId,t.title,t.description,t.order_index,c.teacher_id
            FROM tests t
            JOIN modules m ON m.id = t.module_id
            JOIN courses c ON c.id = m.course_id 
            WHERE t.id = :id 
            """)
    Optional<FullTestView> findTeachersTestViewById(@Param("id") Long id);

    @Query("SELECT id,test_id,question,order_index FROM questions q WHERE q.test_id = :id")
    List<FullTestView.Question> findAllQuestionsInTestByTestId(@Param("id") Long id);

    @Query("SELECT id,question_id,option,is_correct FROM options o WHERE o.question_id IN :ids")
    List<FullTestView.Option> findAllOptionsInTestByQuestionIds(@Param("ids") Collection<Long> ids);
}
