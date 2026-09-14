package course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.markers.Account;

import java.util.List;

public interface UserEnrollmentProviderPort {
    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<Account> userId);
}
