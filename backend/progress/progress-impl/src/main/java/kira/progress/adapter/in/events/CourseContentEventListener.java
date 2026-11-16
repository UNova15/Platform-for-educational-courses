package kira.progress.adapter.in.events;

import kira.course.events.ContentChangeEvent;
import kira.progress.application.port.in.resetprogress.ResetProgressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CourseContentEventListener {
    private final ResetProgressUseCase resetProgressUseCase;

    @EventListener(condition = "#event.wasPublished()")
    public void onResetProgressEvent(ContentChangeEvent event) {
        var resetRecord = CourseEventMapper.mapToResetRecord(event);

        resetProgressUseCase.reset(resetRecord);
    }
}
