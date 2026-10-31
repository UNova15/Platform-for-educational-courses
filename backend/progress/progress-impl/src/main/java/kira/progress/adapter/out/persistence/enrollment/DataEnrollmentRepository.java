package kira.progress.adapter.out.persistence.enrollment;

import org.springframework.data.repository.CrudRepository;

interface DataEnrollmentRepository extends CrudRepository<EnrollmentEntity, Long> {
    boolean existsByUserIdAndCourseId(long userId, long courseId);
}
