package kira.progress.adapter.out.persistence.enrollment;

import common.domain.Id;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentSavePort;
import kira.progress.domain.enrollment.Enrollment;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EnrollmentPersistenceAdapter implements EnrollmentLoadPort, EnrollmentSavePort {
    private final DataEnrollmentRepository repository;
    private final EnrollmentMapper mapper;

    @Override
    public boolean isEnrollmentExist(Id<User> userId, Id<Course> courseId) {
        var id = new EnrollmentEntity.EnrollmentKey(userId.value(), courseId.value());
        return repository.existsById(id);
    }

    @Override
    public void save(Enrollment enrollment) {
        EnrollmentEntity entity = mapper.toEntity(enrollment);
        repository.save(entity);
    }
}
