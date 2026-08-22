package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.course.application.port.in.module.create.ModuleCreateCommand;
import refactor.course.application.port.in.module.create.ModuleCreateResult;
import refactor.course.application.port.in.module.create.ModuleCreateUseCase;
import refactor.course.application.port.in.module.remove.ModuleRemoveUseCase;
import refactor.course.application.port.in.module.update.ModuleUpdateCommand;
import refactor.course.application.port.in.module.update.ModuleUpdateUseCase;
import refactor.infrastructure.accesstoken.TokenPayload;

@RestController
@RequiredArgsConstructor
public class ModuleManageController {
    private final ModuleCreateUseCase createUseCase;
    private final ModuleUpdateUseCase updateUseCase;
    private final ModuleRemoveUseCase removeUseCase;

    @PostMapping("/courses/{courseId}/modules")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleCreateResult createModule(
            @Valid @RequestBody ModuleCreateCommand createCommand,
            @PathVariable long courseId,
            @AuthenticationPrincipal TokenPayload token) {
        return createUseCase.createModule(createCommand, courseId, token.userId());
    }

    @DeleteMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeModule(@PathVariable long moduleId, @AuthenticationPrincipal TokenPayload token) {
        removeUseCase.removeModule(moduleId, token.userId());
    }

    @PutMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateModule(
            @Valid @RequestBody ModuleUpdateCommand command,
            @PathVariable long moduleId,
            @AuthenticationPrincipal TokenPayload token) {
        updateUseCase.updateModule(command, moduleId, token.userId());
    }
}
