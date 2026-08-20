package refactor.course.application.port.in.test.command.remove;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestRemoveUseCase {
    void removeTest(@Valid RemoveTestCommand command);
}
