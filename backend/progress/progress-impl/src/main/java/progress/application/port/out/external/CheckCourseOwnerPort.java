package progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

public interface CheckCourseOwnerPort {
    boolean isTeacherCourseOwner(Id<User> teacherId, Id<Course> courseId);

    boolean isTeacherLessonOwner(Id<User> teacherId, Id<Lesson> lessonId);

    boolean isTeacherTestOwner(Id<User> teacherId, Id<Test> testId);
}
