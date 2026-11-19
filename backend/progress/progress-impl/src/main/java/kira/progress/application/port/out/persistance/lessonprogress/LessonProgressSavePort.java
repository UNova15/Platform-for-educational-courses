package kira.progress.application.port.out.persistance.lessonprogress;

import kira.progress.domain.lessonprogress.LessonProgress;

public interface LessonProgressSavePort {
    void save(LessonProgress lessonProgress);
}
