package refactor.course.adapter.in.web;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.common.domain.Id;
import refactor.course.application.port.in.test.create.TestCreateCommand;
import refactor.course.application.port.in.test.create.TestCreateResult;
import refactor.course.application.port.in.test.create.TestCreateUseCase;
import refactor.course.application.port.in.test.remove.TestRemoveUseCase;
import refactor.course.application.port.in.test.update.TestUpdateCommand;
import refactor.course.application.port.in.test.update.TestUpdateUseCase;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.internal.test.Test;
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
            @PathVariable("moduleId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return createUseCase.createTest(command, userId, moduleId);
    }

    @DeleteMapping("/tests/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTest(@PathVariable("testId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        removeUseCase.removeTest(userId, testId);
    }

    @PutMapping("/tests/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTest(
            @Valid @RequestBody TestUpdateCommand command,
            @PathVariable("testId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        updateUseCase.updateTest(command, testId, userId);
    }
}
