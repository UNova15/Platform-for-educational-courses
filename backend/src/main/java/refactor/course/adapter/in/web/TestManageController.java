package refactor.course.adapter.in.web;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.course.application.port.in.test.create.TestCreateCommand;
import refactor.course.application.port.in.test.create.TestCreateResult;
import refactor.course.application.port.in.test.create.TestCreateUseCase;
import refactor.course.application.port.in.test.remove.TestRemoveUseCase;
import refactor.course.application.port.in.test.update.TestUpdateCommand;
import refactor.course.application.port.in.test.update.TestUpdateUseCase;
import refactor.infrastructure.accesstoken.TokenPayload;

@RestController
@RequiredArgsConstructor
public class TestManageController {
    private final TestCreateUseCase createUseCase;
    private final TestUpdateUseCase updateUseCase;
    private final TestRemoveUseCase removeUseCase;

    @PostMapping("/modules/{moduleId}/tests")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.CREATED)
    public TestCreateResult createTest(
            @Valid @RequestBody TestCreateCommand command,
            @PathVariable long moduleId,
            @AuthenticationPrincipal TokenPayload token) {
        return createUseCase.createTest(command, token.userId(), moduleId);
    }

    @DeleteMapping("/tests/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTest(@PathVariable long testId, @AuthenticationPrincipal TokenPayload token) {
        removeUseCase.removeTest(token.userId(), testId);
    }

    @PutMapping("/tests/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTest(
            @Valid @RequestBody TestUpdateCommand command,
            @PathVariable long testId,
            @AuthenticationPrincipal TokenPayload token) {
        updateUseCase.updateTest(command, testId, token.userId());
    }
}
