package kira.notification.application.port.out.internal;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.domain.Course;
import kira.notification.domain.User;

public interface EnrollmentProviderPort {
    List<Id<User>> findEnrolledUsers(Id<Course> courseId);
}
