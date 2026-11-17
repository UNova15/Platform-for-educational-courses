package kira.notification.application.port.out.internal;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.User;

public interface EnrollmentProviderPort {
    List<Id<User>> findEnrolledUsers(Id<Course> courseId);
}
