package kira.progress.application.service.core;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.application.port.in.resetprogress.ResetProgressUseCase;
import kira.progress.application.port.in.resetprogress.ResetRecord;
import kira.progress.application.port.out.events.EventPublisherPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressRemovePort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptRemovePort;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.event.ResetProgressEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ResetProgressService implements ResetProgressUseCase {
    private final EventPublisherPort eventPublisherPort;

    private final LessonProgressRemovePort lessonProgressRemovePort;
    private final TestAttemptRemovePort testAttemptRemovePort;

    // при росте количества констант/объема логики вынести в реестр стратегий
    @Override
    public void reset(ResetRecord resetRecord) {
        List<Id<User>> studentIds =
                switch (resetRecord.resourceType()) {
                    case LESSON -> {
                        Id<Lesson> lessonId = Id.of(resetRecord.resourceId().value());
                        yield lessonProgressRemovePort.removeAllByLessonId(lessonId);
                    }
                    case TEST -> {
                        Id<Test> testId = Id.of(resetRecord.resourceId().value());
                        yield testAttemptRemovePort.removeAllByTestId(testId);
                    }
                };

        List<Long> mappedIds = studentIds.stream().map(Id::value).toList();

        if (!mappedIds.isEmpty()) {
            eventPublisherPort.publish(new ResetProgressEvent(
                    resetRecord.resourceType(), resetRecord.action(), resetRecord.name(), mappedIds));
        }
    }
}
