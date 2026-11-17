package kira.progress.application.service.core;

import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import kira.progress.application.exception.EnrollmentAlreadyExistException;
import kira.progress.application.port.in.enrollment.UserEnrollmentUseCase;
import kira.progress.application.port.out.events.EventPublicationPort;
import kira.progress.application.port.out.external.CheckCourseStatusPort;
import kira.progress.application.port.out.external.CourseExistCheckPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentSavePort;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.enrollment.Enrollment;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import kira.progress.event.CourseEnrollEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EnrollmentService implements UserEnrollmentUseCase {
    private final CourseExistCheckPort courseCheckPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final EnrollmentSavePort enrollmentSavePort;

    private final CheckCourseStatusPort courseStatusPort;
    private final EventPublicationPort eventPublicationPort;

    public void enrollToCourse(Id<User> studentId, Id<Course> courseId) {
        if (!courseCheckPort.isCourseExist(courseId)) {
            throw new ResourceNotFoundException(ProgressAggregateType.COURSE, courseId);
        }

        if (enrollmentLoadPort.isEnrollmentExist(studentId, courseId)) {
            throw new EnrollmentAlreadyExistException(studentId, courseId);
        }

        if (!courseStatusPort.isCoursePublished(courseId)) {
            throw new RuleConflictException(
                    "Cannot enroll in an unpublished course", ProgressAggregateType.COURSE, courseId);
        }

        var enrollment = Enrollment.create(studentId, courseId);
        enrollmentSavePort.save(enrollment);

        eventPublicationPort.publish(new CourseEnrollEvent(studentId.value(), courseId.value()));
    }
}
