package kira.progress.application.port.out.persistance.testattempt;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface TestAttemptRemovePort {
    List<Id<User>> removeAllByTestId(Id<Test> testId);
}
