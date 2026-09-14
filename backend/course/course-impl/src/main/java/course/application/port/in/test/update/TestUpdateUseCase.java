package course.application.port.in.test.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.test.Test;

@Validated
public interface TestUpdateUseCase {
    void updateTest(@Valid TestUpdateCommand command, @NotNull Id<Test> testId, @NotNull Id<Account> teacherId);
}
