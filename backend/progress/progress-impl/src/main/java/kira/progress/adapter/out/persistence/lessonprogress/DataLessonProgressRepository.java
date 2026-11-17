package kira.progress.adapter.out.persistence.lessonprogress;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface DataLessonProgressRepository extends CrudRepository<LessonProgressEntity, Long> {

    @Query("DELETE FROM progress.lessons_progress WHERE lesson_id = :lessonId RETURNING user_id")
    List<Long> removeAllByLessonIdAndReturnUserIds(@Param("lessonId") long lessonId);

    boolean existsByUserIdAndLessonId(long userId, long lessonId);
}
