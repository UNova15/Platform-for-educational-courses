package kira.progress.adapter.out.persistence.enrollment;

import org.springframework.data.repository.CrudRepository;

public interface DataEnrollmentRepository extends CrudRepository<EnrollmentEntity, EnrollmentEntity.EnrollmentKey> {}
