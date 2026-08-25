package org.platform.platformforeducationalcourses.domain.ports.persistance;

import java.util.List;
import java.util.Optional;
import refactor.course.domain.internal.lesson.Lesson;

public interface LessonRepository {

    List<Lesson> saveAll(List<Lesson> lessons);

    Lesson save(Lesson lesson);

    List<Lesson> findAllByModuleIdIn(List<Long> ids);

    Optional<Lesson> findById(long id);

    void delete(long id);
}
