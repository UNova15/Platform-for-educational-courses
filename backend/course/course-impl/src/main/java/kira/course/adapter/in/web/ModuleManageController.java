package kira.course.adapter.in.web;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import kira.course.application.port.in.module.create.ModuleCreateCommand;
import kira.course.application.port.in.module.create.ModuleCreateResult;
import kira.course.application.port.in.module.create.ModuleCreateUseCase;
import kira.course.application.port.in.module.remove.ModuleRemoveUseCase;
import kira.course.application.port.in.module.update.ModuleUpdateCommand;
import kira.course.application.port.in.module.update.ModuleUpdateUseCase;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ModuleManageController {
    private final ModuleCreateUseCase createUseCase;
    private final ModuleUpdateUseCase updateUseCase;
    private final ModuleRemoveUseCase removeUseCase;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> createModule(
            @Valid @RequestBody ModuleCreateCommand createCommand,
            @PathVariable("courseId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        Result<ModuleCreateResult, ModuleCreateUseCase.CreateError> result =
                createUseCase.createModule(createCommand, courseId, userId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case COURSE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }

    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<?> removeModule(
            @PathVariable("moduleId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        Result<Void, ModuleRemoveUseCase.RemoveError> result = removeUseCase.removeModule(moduleId, userId);

        return result.fold(success -> ResponseEntity.ok().build(), error -> switch (error) {
            case COURSE_ARCHIVED, MODULE_NOT_EMPTY ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case MODULE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        });
    }

    @PutMapping("/modules/{moduleId}")
    public ResponseEntity<?> updateModule(
            @Valid @RequestBody ModuleUpdateCommand command,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        Result<Void, ModuleUpdateUseCase.UpdateError> result = updateUseCase.updateModule(command, moduleId, userId);

        return result.fold(success -> ResponseEntity.ok().build(), error -> switch (error) {
            case MODULE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }
}
