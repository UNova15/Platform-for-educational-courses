package kira.course.application.port.out.persistance.course;

import common.domain.Id;
import java.util.Optional;
import kira.course.domain.course.Course;

public interface CourseLoadPort {
    Optional<Course> loadById(Id<Course> id);

    boolean isExist(Id<Course> id);
}
