package refactor.course.application.port.out.persistance.course;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;

import java.util.Optional;

public interface CourseLoadPort {
    Optional<Course> loadById(Id<Course> id);

    boolean isExist(Id<Course> id);
}
