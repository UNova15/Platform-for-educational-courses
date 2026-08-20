package refactor.course.application.port.in.test.command.update;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestUpdateUseCase {
    void updateTest(@Valid TestUpdateCommand command);
}
