package refactor.course.application.port.out.persistance.course;

import refactor.course.domain.internal.course.Course;

public interface CourseSavePort {

    Course save(Course course);
}
