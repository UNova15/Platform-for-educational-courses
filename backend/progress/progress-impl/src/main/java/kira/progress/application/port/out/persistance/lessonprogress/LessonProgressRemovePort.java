package kira.progress.application.port.out.persistance.lessonprogress;

import common.domain.entity.Id;
import kira.progress.domain.markers.Lesson;

public interface LessonProgressRemovePort {
    void removeAllByLessonId(Id<Lesson> lessonId);
}
