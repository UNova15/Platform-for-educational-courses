package kira.notification.application.port.out.internal;

import common.domain.entity.Id;
import kira.notification.domain.Course;
import kira.notification.domain.User;

import java.util.Optional;

public interface CourseProviderPort {
    Optional<CourseDetails> getCourseDetails(Id<Course> courseId);

    record CourseDetails(String name, Id<User> teacherId) {}
}
