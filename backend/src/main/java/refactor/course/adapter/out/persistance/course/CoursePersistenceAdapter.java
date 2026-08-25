package refactor.course.adapter.out.persistance.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.course.application.port.out.persistance.course.CourseLoadPort;
import refactor.course.domain.internal.course.Course;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CoursePersistenceAdapter implements CourseLoadPort {
    private final DataCourseRepository courseRepository;


    @Override
    public Optional<Course> loadById(long id) {
        Optional<CourseEntity> entity = courseRepository.findById(id);

        if (entity.isEmpty()) return Optional.empty();

        Set<Long> modulesIds =
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }
}
