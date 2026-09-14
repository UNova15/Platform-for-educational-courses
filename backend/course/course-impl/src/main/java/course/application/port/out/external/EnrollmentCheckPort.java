package course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.markers.Account;

public interface EnrollmentCheckPort {
    boolean isUserEnrolledInCourse(Id<Account> studentId, Id<Course> courseId);
}
