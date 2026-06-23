package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.*;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.service.domain.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher/courses/{courseId}/modules/{moduleId}")
@PreAuthorize("hasRole('TEACHER')")
@AllArgsConstructor
public class TeacherTestController {
    private final TestService testService;
    private final TestMapper testMapper;

    @PostMapping
    @PreAuthorize("@courseSecurity.canManagedModule(#securityUser.id,#courseId,#moduleId)")
    public ResponseEntity<TestCreateResponse> createTest(@PathVariable long courseId,
                                                         @PathVariable long moduleId,
                                                         @AuthenticationPrincipal SecurityUser securityUser,
                                                         @Valid @RequestBody TestCreateRequest request) {
        TestCreateDto dto = testMapper.toTestCreateDto(request);
        TestCreateResponse response = testService.createTest(dto, moduleId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<Void> deleteTest(@PathVariable long testId,
                                           @PathVariable long moduleId,
                                           @PathVariable long courseId,
                                           @AuthenticationPrincipal SecurityUser securityUser) {
        testService.deleteTest(testId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<Void> updateTest(@PathVariable long testId,
                                           @PathVariable long moduleId,
                                           @PathVariable long courseId,
                                           @Valid @RequestBody TestUpdateRequest updateRequest,
                                           @AuthenticationPrincipal SecurityUser securityUser) {
        TestUpdateDto updateDto = testMapper.toTestUpdateDto(updateRequest, testId, moduleId);
        testService.updateTest(updateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("{testId}")
    @PreAuthorize("@courseSecurity.canManagedTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<?> getTest(@PathVariable long testId,
                                     @PathVariable long moduleId,
                                     @PathVariable long courseId,
                                     @AuthenticationPrincipal SecurityUser securityUser) {
        TestFindResponse response = testService.getTest(testId);
        return ResponseEntity.ok(response);
    }
}
