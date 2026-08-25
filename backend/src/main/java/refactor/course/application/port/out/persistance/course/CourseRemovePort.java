package refactor.course.application.port.out.persistance.course;

import refactor.course.domain.internal.course.Course;

public interface CourseRemovePort {
    void remove(Course course);
}
