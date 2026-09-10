package refactor.progress.application.port.out.persistance;

import refactor.progress.domain.lessonprogress.LessonProgress;

public interface LessonProgressSavePort {
    void save(LessonProgress lessonProgress);
}
