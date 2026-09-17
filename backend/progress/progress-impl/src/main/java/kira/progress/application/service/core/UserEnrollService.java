package kira.progress.application.service.core;

import common.domain.Id;
import common.exception.ResourceNotFoundException;
import kira.progress.application.exceptions.EnrollmentExistException;
import kira.progress.application.exceptions.ProgressExceptionCode;
import kira.progress.application.port.in.enrollment.UserEnrollmentUseCase;
import kira.progress.application.port.out.external.CourseExistCheckPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentSavePort;
import kira.progress.domain.enrollment.Enrollment;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEnrollService implements UserEnrollmentUseCase {
    private final CourseExistCheckPort courseCheckPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final EnrollmentSavePort enrollmentSavePort;

    public void enrollToCourse(Id<User> userId, Id<Course> courseId) {
        if (!courseCheckPort.isCourseExist(courseId)) {
            throw new ResourceNotFoundException(
                    ProgressExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId);
        }

        if (enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new EnrollmentExistException(userId, courseId);
        }

        Enrollment enrollment = Enrollment.createNew(userId, courseId);
        enrollmentSavePort.save(enrollment);
    }
}
