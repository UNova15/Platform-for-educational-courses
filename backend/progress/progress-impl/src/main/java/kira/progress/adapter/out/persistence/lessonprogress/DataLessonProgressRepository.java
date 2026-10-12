package kira.progress.adapter.out.persistence.lessonprogress;

import org.springframework.data.repository.CrudRepository;

interface DataLessonProgressRepository
        extends CrudRepository<LessonProgressEntity, LessonProgressEntity.LessonProgressKey> {}
