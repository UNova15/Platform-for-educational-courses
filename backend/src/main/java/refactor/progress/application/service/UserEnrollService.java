package refactor.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.common.exception.domain.CourseNotFoundException;
import refactor.progress.application.exception.EnrollmentAlreadyExistException;
import refactor.progress.application.port.in.enrollment.UserEnrollmentUseCase;
import refactor.progress.application.port.out.external.CourseExistCheckPort;
import refactor.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import refactor.progress.application.port.out.persistance.enrollment.EnrollmentSavePort;
import refactor.progress.domain.enrollment.Enrollment;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

@Service
@RequiredArgsConstructor
public class UserEnrollService implements UserEnrollmentUseCase {
    private final CourseExistCheckPort courseCheckPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final EnrollmentSavePort enrollmentSavePort;

    public void enrollToCourse(Id<User> userId, Id<Course> courseId) {
        if (!courseCheckPort.isCourseExist(courseId)) {
            throw new CourseNotFoundException(courseId.value(), userId.value());
        }

        if (enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new EnrollmentAlreadyExistException(userId, courseId);
        }

        Enrollment enrollment = Enrollment.createNew(userId,courseId);
        enrollmentSavePort.save(enrollment);
    }
}
