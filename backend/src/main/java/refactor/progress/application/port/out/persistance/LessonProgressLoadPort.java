package refactor.progress.application.port.out.persistance;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

public interface LessonProgressLoadPort {
    boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId);
}
