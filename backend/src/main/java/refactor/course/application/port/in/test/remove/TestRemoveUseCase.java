package refactor.course.application.port.in.test.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.test.Test;

@Validated
public interface TestRemoveUseCase {
    void removeTest(@NotNull Id<User> requesterId, @NotNull Id<Test> testId);
}
