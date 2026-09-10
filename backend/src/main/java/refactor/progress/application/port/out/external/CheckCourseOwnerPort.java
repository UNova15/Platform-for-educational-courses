package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

public interface CheckCourseOwnerPort {
    boolean isTeacherCourseOwner(Id<User> teacherId, Id<Course> courseId);

    boolean isTeacherLessonOwner(Id<User> teacherId, Id<Lesson> lessonId);

    boolean isTeacherTestOwner(Id<User> teacherId, Id<Test> testId);
}
