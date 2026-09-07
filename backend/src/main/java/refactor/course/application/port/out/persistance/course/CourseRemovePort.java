package refactor.course.application.port.out.persistance.course;

import refactor.course.domain.course.Course;

public interface CourseRemovePort {
    void remove(Course course);
}
