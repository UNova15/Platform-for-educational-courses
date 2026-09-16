package kira.progress.application.port.out.external;


import common.domain.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface CheckCourseOwnerPort {
    boolean isTeacherCourseOwner(Id<User> teacherId, Id<Course> courseId);

    boolean isTeacherLessonOwner(Id<User> teacherId, Id<Lesson> lessonId);

    boolean isTeacherTestOwner(Id<User> teacherId, Id<Test> testId);
}
