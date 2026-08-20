package refactor.course.application.port.in.module.query;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleQueryUseCase {
    ModuleQueryResult findModule(@PositiveOrZero long moduleId);
}
