package kira.progress.application.port.out.persistance.lessonprogress;


import common.domain.Id;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;

public interface LessonProgressLoadPort {
    boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId);
}
