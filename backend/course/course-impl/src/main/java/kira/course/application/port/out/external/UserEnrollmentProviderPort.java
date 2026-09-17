package kira.course.application.port.out.external;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;

import java.util.List;

public interface UserEnrollmentProviderPort {
    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId);
}
