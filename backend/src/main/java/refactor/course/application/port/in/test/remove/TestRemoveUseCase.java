package refactor.course.application.port.in.test.remove;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestRemoveUseCase {
    void removeTest(@PositiveOrZero long requesterId, @PositiveOrZero long testId);
}
