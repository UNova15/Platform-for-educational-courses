package kira.progress.application.port.out.persistance.enrollment;


import common.domain.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

public interface EnrollmentLoadPort {
    boolean isEnrollmentExist(Id<User> userId, Id<Course> courseId);
}
