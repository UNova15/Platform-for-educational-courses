package kira.progress.application.port.in.testprogress;

import common.domain.entity.Id;
import kira.progress.domain.markers.Test;

public interface RemoveTestAttemptUseCase {
    void removeAttempt(Id<Test> testId);
}
