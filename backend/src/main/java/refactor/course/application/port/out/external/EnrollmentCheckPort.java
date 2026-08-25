package refactor.course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

public interface EnrollmentCheckPort {
    boolean isUserEnrolledInCourse(Id<User> studentId, Id<Course> courseId);
}
