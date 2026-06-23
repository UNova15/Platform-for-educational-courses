package org.platform.platformforeducationalcourses.repository;

import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ProgressRepository extends CrudRepository<LessonProgress, Long> {
    boolean existsByUserIdAndLessonId(long userId, long lessonId);

    List<LessonProgress> findAllByUserIdAndLessonIdIn(long userId, Collection<Long> lessonIds);
}
