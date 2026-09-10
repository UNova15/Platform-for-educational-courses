package refactor.progress.application.port.out.persistance.lessonprogress;

import refactor.progress.domain.lessonprogress.LessonProgress;

public interface LessonProgressSavePort {
    void save(LessonProgress lessonProgress);
}
