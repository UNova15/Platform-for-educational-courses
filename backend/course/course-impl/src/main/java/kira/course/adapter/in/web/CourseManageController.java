package kira.course.adapter.in.web;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.application.port.in.course.create.CourseCreateResponse;
import kira.course.application.port.in.course.create.CourseCreateUseCase;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseManageController {
    private final CourseCreateUseCase createUseCase;
    private final CourseRemoveUseCase removeUseCase;
    private final CourseUpdateUseCase updateUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreateResponse createCourse(
            @Valid @RequestBody CourseCreateCommand command, @AuthenticationPrincipal TokenPayload token) {

        Id<User> userId = Id.of(token.userId());
        return createUseCase.createCourse(command, userId);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> removeCourse(
            @PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        Result<Void, CourseRemoveUseCase.RemoveError> result = removeUseCase.removeCourse(userId, courseId);

        return result.fold(success -> ResponseEntity.noContent().build(), error -> switch (error) {
            case ACCESS_DENIED -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case COURSE_IS_NOT_IN_DRAFT ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(
            @Valid @RequestBody CourseUpdateCommand command,
            @PathVariable("courseId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        Result<Void, CourseUpdateUseCase.UpdateError> result = updateUseCase.updateCourse(command, courseId, userId);

        return result.fold(success -> ResponseEntity.noContent().build(), error -> switch (error) {
            case COURSE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENIED -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_IS_ARCHIVED ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }
}
