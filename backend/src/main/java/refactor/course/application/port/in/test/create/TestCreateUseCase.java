package refactor.course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestCreateUseCase {
    TestCreateResult createTest(
            @Valid TestCreateCommand command, @PositiveOrZero long teacherId, @PositiveOrZero long moduleId);
}
