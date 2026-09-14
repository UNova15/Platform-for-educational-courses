package progress.application.port.in.testprogress;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

@Validated
public interface StartTestAttemptUseCase {
    void startTestAttempt(@NotNull Id<User> userId,@NotNull Id<Test> testId);
}
