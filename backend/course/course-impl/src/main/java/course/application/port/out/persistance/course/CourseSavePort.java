package course.application.port.out.persistance.course;

import refactor.course.implementation.domain.course.Course;

public interface CourseSavePort {

    Course save(Course course);
}
