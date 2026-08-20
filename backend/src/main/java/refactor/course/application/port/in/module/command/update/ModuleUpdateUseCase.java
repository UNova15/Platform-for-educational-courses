package refactor.course.application.port.in.module.command.update;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleUpdateUseCase {
    void updateModule(@Valid ModuleUpdateCommand updateCommand);
}
