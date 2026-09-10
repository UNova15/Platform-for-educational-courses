package refactor.progress.application.port.out.persistance;

import refactor.progress.domain.enrollment.Enrollment;

public interface EnrollmentSavePort {

    void save(Enrollment enrollment);
}
