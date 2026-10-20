package kira.progress.adapter.in.events;

import common.domain.entity.Id;
import kira.course.events.TestUpdatedEvent;
import kira.progress.application.port.in.testprogress.RemoveTestAttemptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestUpdatedEventListener {
    private final RemoveTestAttemptUseCase removeTestAttemptUseCase;

    @EventListener(condition = "#event.wasPublished()")
    public void handleTestUpdatedEvent(TestUpdatedEvent event){
        removeTestAttemptUseCase.removeAttempt(Id.of(event.testId()));
    }
}
