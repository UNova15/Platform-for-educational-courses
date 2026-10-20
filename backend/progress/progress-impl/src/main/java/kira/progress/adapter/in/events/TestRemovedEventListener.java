package kira.progress.adapter.in.events;

import common.domain.entity.Id;
import kira.course.events.TestRemovedEvent;
import kira.progress.application.port.in.testprogress.RemoveTestAttemptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestRemovedEventListener {
    private final RemoveTestAttemptUseCase removeAttemptUseCase;

    @EventListener(condition = "#event.wasPublished()")
    public void handleTestRemovedEvent(TestRemovedEvent event) {
        removeAttemptUseCase.removeAttempt(Id.of(event.testId()));
    }
}
