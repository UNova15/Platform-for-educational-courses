package refactor.course.application.port.in.module.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleCreateUseCase {
    ModuleCreateResult createModule(
            @Valid ModuleCreateCommand createCommand, @PositiveOrZero long courseId, @PositiveOrZero long teacherId);
}
