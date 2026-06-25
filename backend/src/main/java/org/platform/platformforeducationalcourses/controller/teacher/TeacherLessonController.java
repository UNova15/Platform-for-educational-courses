package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.platform.platformforeducationalcourses.service.domain.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules/{moduleId}/lessons")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherLessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @PostMapping
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public ResponseEntity<LessonCreateResponse> createLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @Valid @RequestBody CourseLessonCreateRequest request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {
        LessonCreateDto createDto = lessonMapper.toLessonCreateDto(moduleId, request);
        LessonCreateResponse response = lessonService.createLesson(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{lessonId}")
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public ResponseEntity<Void> updateLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @Valid @RequestBody LessonUpdateRequest request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {
        LessonUpdateDto updateDto = lessonMapper.toLessonUpdateDto(lessonId, moduleId, request);
        lessonService.updateLesson(updateDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{lessonId}")
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {
        lessonService.deleteLesson(lessonId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{lessonId}")
    @PreAuthorize("@courseSecurity.canManagedLesson(#userPrincipal.id,#courseId,#moduleId,#lessonId)")
    public ResponseEntity<LessonFindResponse> getLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser userPrincipal) {
        LessonFindResponse lesson = lessonService.findLesson(lessonId);
        return ResponseEntity.ok(lesson);
    }
}
