package refactor.course.adapter.out.persistance.course;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DataCourseRepository extends CrudRepository<CourseEntity, Long> {
    List<CourseEntity> findAllByTeacherId(long userId);

    Optional<CourseEntity> findByIdAndTeacherId(long userId, long courseId);

    List<CourseEntity> findCoursesByIdIn(Collection<Long> ids);
}
