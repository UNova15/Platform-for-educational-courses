package kira.course.api;

public interface CheckOwnerQuery {
    boolean isTeacherCourseOwner(long teacherId, long courseId);

    boolean isTeacherLessonOwner(long teacherId, long lessonId);

    boolean isTeacherTestOwner(long teacherId, long testId);
}
