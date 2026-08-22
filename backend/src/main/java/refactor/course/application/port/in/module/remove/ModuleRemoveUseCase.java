package refactor.course.application.port.in.module.remove;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleRemoveUseCase {
    void removeModule( @PositiveOrZero long moduleId, @PositiveOrZero long teacherId);
}
