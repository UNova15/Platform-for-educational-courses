package kira.progress.application.port.in.lessonprogress;

import common.domain.entity.Id;
import kira.progress.domain.markers.Lesson;

public interface RemoveLessonProgressUseCase {
    void removeLessonProgress(Id<Lesson> lessonId);
}
