package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import refactor.course.application.port.in.lesson.query.LessonQueryResult;
import refactor.course.application.port.in.course.command.create.CourseBulkLessonCommand;
import refactor.course.implementation.application.port.in.lesson.create.LessonCreateResult;
import refactor.course.implementation.application.port.in.lesson.update.LessonUpdateCommand;
import refactor.course.application.service.LessonRemoveService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.auth.adapter.out.security.model.SecurityUser;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules/{moduleId}/lessons")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherLessonController {
    private final LessonRemoveService lessonRemoveService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public LessonCreateResult createLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @Valid @RequestBody CourseBulkLessonCommand request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        return lessonRemoveService.createLesson(request, moduleId);
    }

    @PutMapping("{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public void updateLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @Valid @RequestBody LessonUpdateCommand request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        lessonRemoveService.updateLesson(request, moduleId, lessonId);
    }

    @DeleteMapping("{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public void deleteLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        lessonRemoveService.deleteLesson(lessonId);
    }

    @GetMapping("{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public LessonQueryResult getLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        return lessonRemoveService.findLesson(lessonId);
    }
}
