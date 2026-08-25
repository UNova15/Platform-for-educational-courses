package refactor.course.application.port.in.test.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.test.Test;

@Validated
public interface TestUpdateUseCase {
    void updateTest(@Valid TestUpdateCommand command, @NotNull Id<Test> testId, @NotNull Id<User> teacherId);
}
