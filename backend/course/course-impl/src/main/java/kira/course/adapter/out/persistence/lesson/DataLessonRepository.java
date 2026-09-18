package kira.course.adapter.out.persistence.lesson;

import org.springframework.data.repository.CrudRepository;

public interface DataLessonRepository extends CrudRepository<LessonEntity,Long> {

    int countByModuleId(Long moduleId);
}
