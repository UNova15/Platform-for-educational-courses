package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.Collection;
import java.util.List;
import org.platform.platformforeducationalcourses.persistance.entity.progress.LessonProgressEntity;
import org.springframework.data.repository.CrudRepository;

public interface DataProgressRepository extends CrudRepository<LessonProgressEntity, Long> {
    boolean existsByUserIdAndLessonId(long userId, long lessonId);

    List<LessonProgressEntity> findAllByUserIdAndLessonIdIn(long userId, Collection<Long> lessonIds);
}
