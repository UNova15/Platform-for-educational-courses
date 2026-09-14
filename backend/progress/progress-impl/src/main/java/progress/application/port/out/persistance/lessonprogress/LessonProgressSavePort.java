package progress.application.port.out.persistance.lessonprogress;

import refactor.progress.implementation.domain.lessonprogress.LessonProgress;

public interface LessonProgressSavePort {
    void save(LessonProgress lessonProgress);
}
