package kira.progress.application.port.out.external;

import common.domain.entity.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface CheckCourseOwnerPort {
    boolean isCourseOwner(Id<User> teacherId, Id<Course> courseId);

    boolean isLessonOwner(Id<User> teacherId, Id<Lesson> lessonId);

    boolean isTestOwner(Id<User> teacherId, Id<Test> testId);
}
