package kira.progress.application.port.out.persistance.testattempt;

import common.domain.entity.Id;
import kira.progress.domain.markers.Test;

public interface TestAttemptRemovePort {
    void removeAllByTestId(Id<Test> testId);
}
