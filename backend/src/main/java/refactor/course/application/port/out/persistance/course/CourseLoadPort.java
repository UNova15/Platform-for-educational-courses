package refactor.course.application.port.out.persistance.course;

import refactor.course.domain.course.Course;

import java.util.Optional;

public interface CourseLoadPort {
    Optional<Course> loadById(long id);
}
