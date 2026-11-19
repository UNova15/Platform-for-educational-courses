package kira.course.application.port.out.external;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;

public interface EnrollmentCheckPort {
    boolean isUserEnrolledInCourse(Id<User> studentId, Id<Course> courseId);
}
