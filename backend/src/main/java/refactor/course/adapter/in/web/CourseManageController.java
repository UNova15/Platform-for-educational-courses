package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.application.port.in.course.command.create.CourseCreateResult;
import refactor.course.application.port.in.course.command.create.CourseCreateUseCase;
import refactor.course.application.port.in.course.command.remove.CourseRemoveUseCase;
import refactor.course.application.port.in.course.command.update.CourseUpdateCommand;
import refactor.course.application.port.in.course.command.update.CourseUpdateUseCase;
import refactor.infrastructure.accesstoken.TokenPayload;

@RestController
@RequestMapping("/courses")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class CourseManageController {
    private final CourseCreateUseCase createUseCase;
    private final CourseRemoveUseCase removeUseCase;
    private final CourseUpdateUseCase updateUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreateResult createCourse(
            @Valid @RequestBody CourseCreateCommand command, @AuthenticationPrincipal TokenPayload token) {

        return createUseCase.createCourseWithContent(command, token.userId());
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCourse(@PathVariable long courseId, @AuthenticationPrincipal TokenPayload token) {

        removeUseCase.removeCourse(token.userId(), courseId);
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(
            @Valid @RequestBody CourseUpdateCommand command,
            @PathVariable long courseId,
            @AuthenticationPrincipal TokenPayload token) {

        updateUseCase.updateCourse(command, courseId, token.userId());
    }
}
