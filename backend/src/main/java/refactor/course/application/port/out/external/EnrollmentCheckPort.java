package refactor.course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.markers.Account;

public interface EnrollmentCheckPort {
    boolean isUserEnrolledInCourse(Id<Account> studentId, Id<Course> courseId);
}
