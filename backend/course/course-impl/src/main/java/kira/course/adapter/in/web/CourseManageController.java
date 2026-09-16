package kira.course.adapter.in.web;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import jakarta.validation.Valid;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.application.port.in.course.create.CourseCreateResult;
import kira.course.application.port.in.course.create.CourseCreateUseCase;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;

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

        Id<User> userId = Id.of(token.userId());
        return createUseCase.createCourseWithContent(command, userId);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCourse(@PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        removeUseCase.removeCourse(userId, courseId);
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(
            @Valid @RequestBody CourseUpdateCommand command,
            @PathVariable("courseId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        updateUseCase.updateCourse(command, courseId, userId);
    }
}
