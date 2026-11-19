package kira.progress.api;

public interface EnrollmentStatusQuery {
    boolean isUserEnrolledInCourse(long studentId, long courseId);
}
