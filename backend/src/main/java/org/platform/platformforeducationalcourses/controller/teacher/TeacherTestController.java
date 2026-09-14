package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.dto.test.*;
import refactor.course.application.port.in.test.query.TestQueryResult;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import refactor.course.implementation.application.port.in.test.create.TestCreateCommand;
import refactor.course.implementation.application.port.in.test.create.TestCreateResult;
import refactor.course.implementation.application.port.in.test.update.TestUpdateCommand;
import refactor.course.implementation.application.service.TestManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.auth.adapter.out.security.model.SecurityUser;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules/{moduleId}")
@PreAuthorize("hasRole('TEACHER')")
@AllArgsConstructor
public class TeacherTestController {
    private final TestManageService testManageService;
    private final TestMapper testMapper;

    @PostMapping
    @PreAuthorize("@courseSecurity.canManagedModule(#securityUser.id,#courseId,#moduleId)")
    public ResponseEntity<TestCreateResult> createTest(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody TestCreateCommand request) {
        TestCreateDto dto = testMapper.toTestCreateDto(request);
        TestCreateResult response = testManageService.createTest(dto, moduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<Void> deleteTest(
            @PathVariable long testId,
            @PathVariable long moduleId,
            @PathVariable long courseId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        testManageService.removeTest(testId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<Void> updateTest(
            @PathVariable long testId,
            @PathVariable long moduleId,
            @PathVariable long courseId,
            @Valid @RequestBody TestUpdateRequest updateRequest,
            @AuthenticationPrincipal SecurityUser securityUser) {
        TestUpdateCommand updateDto = testMapper.toTestUpdateDto(updateRequest, testId, moduleId);
        testManageService.updateTest(updateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<?> getTest(
            @PathVariable long testId,
            @PathVariable long moduleId,
            @PathVariable long courseId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        TestQueryResult response = testManageService.getTest(testId);
        return ResponseEntity.ok(response);
    }
}
