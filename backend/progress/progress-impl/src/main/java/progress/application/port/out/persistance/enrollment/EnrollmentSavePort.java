package progress.application.port.out.persistance.enrollment;

import refactor.progress.implementation.domain.enrollment.Enrollment;

public interface EnrollmentSavePort {

    void save(Enrollment enrollment);
}
