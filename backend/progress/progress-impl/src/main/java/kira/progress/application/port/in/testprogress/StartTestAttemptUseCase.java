package kira.progress.application.port.in.testprogress;

import common.domain.Id;
import jakarta.validation.constraints.NotNull;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface StartTestAttemptUseCase {
    void startTestAttempt(@NotNull Id<User> userId, @NotNull Id<Test> testId);
}
