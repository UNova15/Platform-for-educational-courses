package refactor.course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.user.Account;

import java.util.List;

public interface UserEnrollmentProviderPort {
    List<Id<Course>> findEnrolledCourseIds(Id<Account> userId);
}
