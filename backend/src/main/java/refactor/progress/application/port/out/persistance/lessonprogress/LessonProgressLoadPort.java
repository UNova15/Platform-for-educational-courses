package refactor.progress.application.port.out.persistance.lessonprogress;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.CourseModule;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

import java.util.List;
import java.util.Set;

public interface LessonProgressLoadPort {
    boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId);
}
