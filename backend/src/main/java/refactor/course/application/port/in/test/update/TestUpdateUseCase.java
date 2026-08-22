package refactor.course.application.port.in.test.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestUpdateUseCase {
    void updateTest(@Valid TestUpdateCommand command, @PositiveOrZero long testId, @PositiveOrZero long teacherId);
}
