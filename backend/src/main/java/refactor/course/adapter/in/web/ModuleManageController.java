package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.common.domain.Id;
import refactor.course.application.port.in.module.create.ModuleCreateCommand;
import refactor.course.application.port.in.module.create.ModuleCreateResult;
import refactor.course.application.port.in.module.create.ModuleCreateUseCase;
import refactor.course.application.port.in.module.remove.ModuleRemoveUseCase;
import refactor.course.application.port.in.module.update.ModuleUpdateCommand;
import refactor.course.application.port.in.module.update.ModuleUpdateUseCase;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.module.CourseModule;
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
            @PathVariable("courseId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return createUseCase.createModule(createCommand, courseId, userId);
    }

    @DeleteMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeModule(@PathVariable("moduleId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        removeUseCase.removeModule(moduleId, userId);
    }

    @PutMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateModule(
            @Valid @RequestBody ModuleUpdateCommand command,
            @PathVariable("moduleId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        updateUseCase.updateModule(command, moduleId, userId);
    }
}
