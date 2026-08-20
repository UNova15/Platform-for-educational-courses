package refactor.course.application.port.in.test.command.create;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestCreateUseCase {
    TestCreateResult createTest(@Valid TestCreateCommand command);

}
