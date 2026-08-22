package refactor.course.application.port.out.external;

public interface EnrollmentCheckPort {
    boolean isUserEnrolledInCourse(long studentId, long courseId);
}
