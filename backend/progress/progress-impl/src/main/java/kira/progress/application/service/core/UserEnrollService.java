package kira.progress.application.service.core;

import common.domain.Id;
import common.exception.named.ResourceNotFoundException;
import kira.progress.application.port.in.enrollment.UserEnrollmentUseCase;
import kira.progress.application.port.out.external.CourseExistCheckPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentSavePort;
import kira.progress.application.exception.EnrollmentAlreadyExistException;
import kira.progress.application.exception.ProgressResourceName;
import kira.progress.domain.enrollment.Enrollment;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserEnrollService implements UserEnrollmentUseCase {
    private final CourseExistCheckPort courseCheckPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final EnrollmentSavePort enrollmentSavePort;

    //TODO проверить что курс опубликован (не архив и не драфт)
    public void enrollToCourse(Id<User> userId, Id<Course> courseId) {
        if (!courseCheckPort.isCourseExist(courseId)) {
            throw new ResourceNotFoundException(ProgressResourceName.COURSE, courseId);
        }

        if (enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new EnrollmentAlreadyExistException(userId, courseId);
        }

        var enrollment = Enrollment.create(userId, courseId);
        enrollmentSavePort.save(enrollment);
    }
}
