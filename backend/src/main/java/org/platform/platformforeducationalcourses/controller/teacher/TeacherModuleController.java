package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.module.*;
import org.platform.platformforeducationalcourses.mapper.ModuleMapper;
import org.platform.platformforeducationalcourses.service.domain.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherModuleController {
    private final ModuleService moduleService;
    private final ModuleMapper moduleMapper;


    @PostMapping
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public ResponseEntity<ModuleCreateResponse> createModule(@PathVariable long courseId,
                                                             @Valid @RequestBody ModuleCreateRequest request,
                                                             @AuthenticationPrincipal SecurityUser userPrincipal) {
        ModuleCreateDto moduleDto = moduleMapper.toCreateModuleDto(courseId, request);
        ModuleCreateResponse response = moduleService.createModule(moduleDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @DeleteMapping("{moduleId}")
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public ResponseEntity<Void> deleteModule(@PathVariable long courseId,
                                             @PathVariable long moduleId,
                                             @AuthenticationPrincipal SecurityUser userPrincipal) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("{moduleId}")
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public ResponseEntity<Void> updateModule(@PathVariable long courseId,
                                             @PathVariable long moduleId,
                                             @Valid @RequestBody ModuleUpdateRequest request,
                                             @AuthenticationPrincipal SecurityUser userPrincipal) {

        ModuleUpdateDto updateDto = moduleMapper.toModuleUpdateDto(courseId, moduleId, request);
        moduleService.updateModule(updateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("{moduleId}")
    @PreAuthorize("@courseSecurity.canManagedModule(#userPrincipal.id,#courseId,#moduleId)")
    public ResponseEntity<ModuleFindResponse> getModule(@PathVariable long courseId,
                                                        @PathVariable long moduleId,
                                                        @AuthenticationPrincipal SecurityUser userPrincipal) {
        ModuleFindResponse module = moduleService.findModule(moduleId);
        return ResponseEntity.ok(module);
    }
}
