package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.common.domain.Id;
import refactor.course.application.port.in.course.create.CourseCreateCommand;
import refactor.course.application.port.in.course.create.CourseCreateResult;
import refactor.course.application.port.in.course.create.CourseCreateUseCase;
import refactor.course.application.port.in.course.remove.CourseRemoveUseCase;
import refactor.course.application.port.in.course.update.CourseUpdateCommand;
import refactor.course.application.port.in.course.update.CourseUpdateUseCase;
import refactor.course.domain.user.Account;
import refactor.course.domain.course.Course;
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

        Id<Account> userId = Id.of(token.userId());
        return createUseCase.createCourseWithContent(command, userId);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCourse(@PathVariable("courseId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        removeUseCase.removeCourse(userId, courseId);
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(
            @Valid @RequestBody CourseUpdateCommand command,
            @PathVariable("courseId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        updateUseCase.updateCourse(command, courseId, userId);
    }
}
