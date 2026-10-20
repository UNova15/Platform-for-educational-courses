package kira.progress.adapter.in.events;

import common.domain.entity.Id;
import kira.course.events.LessonRemovedEvent;
import kira.progress.application.port.in.lessonprogress.RemoveLessonProgressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonRemovedEventListener {
    private final RemoveLessonProgressUseCase removeUseCase;

    @EventListener(condition = "#event.wasPublished()")
    public void handleLessonRemovedEvent(LessonRemovedEvent event) {
        removeUseCase.removeLessonProgress(Id.of(event.lessonId()));
    }
}
