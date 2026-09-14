package course.application.port.out.persistance.course;

import refactor.course.implementation.domain.course.Course;

public interface CourseRemovePort {
    void remove(Course course);
}
