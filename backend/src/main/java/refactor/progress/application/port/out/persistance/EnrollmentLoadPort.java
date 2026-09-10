package refactor.progress.application.port.out.persistance;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

public interface EnrollmentLoadPort {
    boolean isEnrollmentExist(Id<User> userId, Id<Course> courseId);
}
