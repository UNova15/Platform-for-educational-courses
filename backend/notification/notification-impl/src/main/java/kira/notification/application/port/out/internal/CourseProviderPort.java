package kira.notification.application.port.out.internal;

import common.domain.entity.Id;
import java.util.Optional;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.Test;
import kira.notification.domain.marker.User;

public interface CourseProviderPort {
    Optional<CourseDetails> getCourseDetails(Id<Course> courseId);

    record CourseDetails(String courseTitle, Id<User> teacherId) {}

    Optional<TestDetails> getTestDetails(Id<Test> testId);

    record TestDetails(String courseTitle, String testTitle, Id<User> teacherId) {}
}
