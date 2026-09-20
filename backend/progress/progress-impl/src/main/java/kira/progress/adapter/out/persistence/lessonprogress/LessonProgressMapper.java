package kira.progress.adapter.out.persistence.lessonprogress;

import kira.progress.domain.lessonprogress.LessonProgress;
import org.springframework.stereotype.Component;

@Component
public class LessonProgressMapper {

    public LessonProgressEntity toEntity(LessonProgress progress) {
        var id = new LessonProgressEntity.LessonProgressKey(
                progress.userId().value(), progress.lessonId().value());
        return new LessonProgressEntity(id, progress.completedAt());
    }
}
