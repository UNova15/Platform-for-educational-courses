package progress.application.port.out.persistance.lessonprogress;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.User;

public interface LessonProgressLoadPort {
    boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId);
}
