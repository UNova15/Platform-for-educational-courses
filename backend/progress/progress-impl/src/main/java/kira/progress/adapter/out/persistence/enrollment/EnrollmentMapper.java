package kira.progress.adapter.out.persistence.enrollment;

import kira.progress.domain.enrollment.Enrollment;
import org.springframework.stereotype.Component;

@Component
class EnrollmentMapper {

    public EnrollmentEntity toEntity(Enrollment enrollment) {
        Long id = enrollment.id() == null ? null : enrollment.id().value();
        return new EnrollmentEntity(
                id, enrollment.courseId().value(), enrollment.userId().value(), enrollment.createdAt());
    }
}
