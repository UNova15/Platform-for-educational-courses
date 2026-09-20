package kira.progress.adapter.out.persistence.enrollment;

import kira.progress.domain.enrollment.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentEntity toEntity(Enrollment enrollment) {
        var key = new EnrollmentEntity.EnrollmentKey(
                enrollment.userId().value(), enrollment.courseId().value());

        return new EnrollmentEntity(key, enrollment.createdAt());
    }
}
