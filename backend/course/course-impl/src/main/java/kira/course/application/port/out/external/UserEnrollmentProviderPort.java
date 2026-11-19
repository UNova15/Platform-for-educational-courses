package kira.course.application.port.out.external;

import common.domain.entity.Id;
import java.util.List;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;

public interface UserEnrollmentProviderPort {
    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId);
}
