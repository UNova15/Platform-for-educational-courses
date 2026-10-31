package kira.progress.adapter.in.events;

import common.domain.entity.Id;
import kira.course.events.LessonUpdatedEvent;
import kira.progress.application.port.in.lessonprogress.RemoveLessonProgressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonUpdatedEventListener {
    private final RemoveLessonProgressUseCase removeUseCase;

    @EventListener(condition = "#event.wasPublished()")
    public void handleLessonUpdatedEvent(LessonUpdatedEvent event) {
        removeUseCase.removeLessonProgress(Id.of(event.lessonId()));
    }
}
