package kira.progress.application.port.out.persistance.enrollment;


import kira.progress.domain.enrollment.Enrollment;

public interface EnrollmentSavePort {

    void save(Enrollment enrollment);
}
