package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import org.platform.platformforeducationalcourses.dto.lesson.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateRequest;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateResponse;
import org.platform.platformforeducationalcourses.service.domain.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.user.adapter.out.security.model.SecurityUser;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules/{moduleId}/lessons")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherLessonController {
    private final LessonService lessonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public LessonCreateResponse createLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @Valid @RequestBody LessonCreateRequest request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        return lessonService.createLesson(request, moduleId);
    }

    @PutMapping("{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public void updateLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @Valid @RequestBody LessonUpdateRequest request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        lessonService.updateLesson(request, moduleId, lessonId);
    }

    @DeleteMapping("{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public void deleteLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        lessonService.deleteLesson(lessonId);
    }

    @GetMapping("{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public LessonFindResponse getLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        return lessonService.findLesson(lessonId);
    }
}
