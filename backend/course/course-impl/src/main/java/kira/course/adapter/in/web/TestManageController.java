package kira.course.adapter.in.web;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResult;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestManageController {
    private final TestCreateUseCase createUseCase;
    private final TestUpdateUseCase updateUseCase;
    private final TestRemoveUseCase removeUseCase;

    @PostMapping("/modules/{moduleId}/tests")
    public ResponseEntity<?> createTest(
            @Valid @RequestBody TestCreateCommand command,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        Result<TestCreateResult, TestCreateUseCase.CreateError> result =
                createUseCase.createTest(command, userId, moduleId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case MODULE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }

    @DeleteMapping("/tests/{testId}")
    public ResponseEntity<?> removeTest(
            @PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        Result<Void, TestRemoveUseCase.RemoveError> result = removeUseCase.removeTest(userId, testId);

        return result.fold(
                success -> ResponseEntity.status(HttpStatus.NO_CONTENT).build(), error -> switch (error) {
                    case TEST_NOT_FOUND ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    case ACCESS_DENY ->
                        ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
                    case COURSE_ARCHIVED ->
                        ResponseEntity.status(HttpStatus.CONFLICT).body(error);
                });
    }

    @PutMapping("/tests/{testId}")
    public ResponseEntity<?> updateTest(
            @Valid @RequestBody TestUpdateCommand command,
            @PathVariable("testId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        Result<Void, TestUpdateUseCase.UpdateError> result = updateUseCase.updateTest(command, testId, userId);

        return result.fold(
                success -> ResponseEntity.status(HttpStatus.NO_CONTENT).build(), error -> switch (error) {
                    case TEST_NOT_FOUND ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    case ACCESS_DENY ->
                        ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
                    case COURSE_ARCHIVED ->
                        ResponseEntity.status(HttpStatus.CONFLICT).body(error);
                });
    }
}
