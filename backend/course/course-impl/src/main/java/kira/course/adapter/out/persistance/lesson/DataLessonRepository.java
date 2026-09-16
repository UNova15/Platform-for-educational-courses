package kira.course.adapter.out.persistance.lesson;

import org.springframework.data.repository.CrudRepository;

public interface DataLessonRepository extends CrudRepository<LessonEntity,Long> {

    int countByModuleId(Long moduleId);
}
