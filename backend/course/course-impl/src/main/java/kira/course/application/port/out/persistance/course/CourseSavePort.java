package kira.course.application.port.out.persistance.course;


import kira.course.domain.course.Course;

public interface CourseSavePort {

    Course save(Course course);
}
