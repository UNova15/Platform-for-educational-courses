package refactor.progress.application.port.in.testprogress;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

@Validated
public interface StartTestAttemptUseCase {
    void startTestAttempt(@NotNull Id<User> userId,@NotNull Id<Test> testId);
}
