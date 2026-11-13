package kira.progress.adapter.out.persistence.lessonprogress;

import kira.progress.domain.lessonprogress.LessonProgress;
import org.springframework.stereotype.Component;

@Component
class LessonProgressMapper {

    public LessonProgressEntity toEntity(LessonProgress progress) {
        Long id = progress.id() == null ? null : progress.id().value();
        return new LessonProgressEntity(
                id, progress.userId().value(), progress.lessonId().value(), progress.completedAt());
    }
}
