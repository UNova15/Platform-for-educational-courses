package refactor.course.application.port.in.module.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleUpdateUseCase {
    void updateModule(
            @Valid ModuleUpdateCommand updateCommand, @PositiveOrZero long moduleId, @PositiveOrZero long teacherId);
}
