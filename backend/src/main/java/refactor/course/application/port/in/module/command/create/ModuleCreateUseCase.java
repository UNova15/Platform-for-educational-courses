package refactor.course.application.port.in.module.command.create;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleCreateUseCase {
    ModuleCreateResult createModule(@Valid ModuleCreateCommand createCommand);
}
