package refactor.course.application.port.in.module.command.remove;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleRemoveUseCase {
    void removeModule(@Valid ModuleRemoveCommand deleteCommand);
}
