package org.platform.platformforeducationalcourses.controller.student;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('STUDENT')")
@RequestMapping("student/enrollments")
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("{courseId}")
    public ResponseEntity<Void> enrollInCourse(@PathVariable long courseId,
                                         @AuthenticationPrincipal SecurityUser securityUser) {
        enrollmentService.enrollToCourse(securityUser.getId(), courseId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
