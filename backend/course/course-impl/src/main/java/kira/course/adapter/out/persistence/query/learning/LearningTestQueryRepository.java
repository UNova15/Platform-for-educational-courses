package kira.course.adapter.out.persistence.query.learning;

import kira.course.adapter.out.persistence.test.TestEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import kira.course.application.port.in.query.learning.StudentsModuleView;
import kira.course.application.port.in.query.learning.StudentsTestView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LearningTestQueryRepository extends CrudRepository<TestEntity, Long> {

    @Query("SELECT id,title,module_id,order_index FROM tests t WHERE t.module_id = :moduleId")
    List<StudentsModuleView.Test> findStudentsModuleTestView(@Param("moduleId") Long moduleId);

    @Query("""
            SELECT t.id,t.module_id,m.course_id,t.order_index,t.title,t.description
            FROM tests t
            JOIN modules m ON t.module_id = m.id
            WHERE t.id = :testId
            """)
    Optional<StudentsTestView> findStudentsTestView(@Param("testId") Long testId);

    @Query("SELECT id,test_id,order_index,content FROM questions q WHERE  q.test_id = :testId")
    List<StudentsTestView.Question> findStudentsTestQuestionsView(@Param("testId") Long testId);

    @Query("SELECT id,question_id,option FROM options o WHERE o.question_id IN :questionsIds")
    List<StudentsTestView.AnswerOption> findStudentsTestOptionsView(@Param("questionsIds") Collection<Long> questionId);
}
