package kira.progress.application.port.out.persistance.lessonprogress;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;

public interface LessonProgressRemovePort {
    List<Id<User>> removeAllByLessonId(Id<Lesson> lessonId);
}
