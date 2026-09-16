package kira.course.adapter.in.web;

import common.domain.Id;
import jakarta.validation.Valid;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import kira.course.application.port.in.module.create.ModuleCreateCommand;
import kira.course.application.port.in.module.create.ModuleCreateResult;
import kira.course.application.port.in.module.create.ModuleCreateUseCase;
import kira.course.application.port.in.module.remove.ModuleRemoveUseCase;
import kira.course.application.port.in.module.update.ModuleUpdateCommand;
import kira.course.application.port.in.module.update.ModuleUpdateUseCase;

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
            @PathVariable("courseId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return createUseCase.createModule(createCommand, courseId, userId);
    }

    @DeleteMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeModule(@PathVariable("moduleId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        removeUseCase.removeModule(moduleId, userId);
    }

    @PutMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateModule(
            @Valid @RequestBody ModuleUpdateCommand command,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        updateUseCase.updateModule(command, moduleId, userId);
    }
}
