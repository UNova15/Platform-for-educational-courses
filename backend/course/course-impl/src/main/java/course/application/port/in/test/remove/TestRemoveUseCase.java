package course.application.port.in.test.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.test.Test;

@Validated
public interface TestRemoveUseCase {
    void removeTest(@NotNull Id<Account> requesterId, @NotNull Id<Test> testId);
}
