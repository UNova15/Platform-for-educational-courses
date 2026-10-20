package kira.progress.application.service.core;

import common.domain.entity.Id;
import kira.progress.application.port.in.testprogress.RemoveTestAttemptUseCase;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptRemovePort;
import kira.progress.domain.markers.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RemoveTestAttemptService implements RemoveTestAttemptUseCase {
    private final TestAttemptRemovePort removePort;

    @Override
    public void removeAttempt(Id<Test> testId) {
        removePort.removeAllByTestId(testId);
    }
}
