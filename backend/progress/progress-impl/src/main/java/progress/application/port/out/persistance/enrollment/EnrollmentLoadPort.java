package progress.application.port.out.persistance.enrollment;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.User;

public interface EnrollmentLoadPort {
    boolean isEnrollmentExist(Id<User> userId, Id<Course> courseId);
}
