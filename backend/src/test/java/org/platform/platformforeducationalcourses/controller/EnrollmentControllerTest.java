package org.platform.platformforeducationalcourses.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.student.EnrollmentController;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController controller;

    @Test
    void enrollInCourse_ReturnsCreated() {
        SecurityUser mockUser = mock(SecurityUser.class);
        when(mockUser.getId()).thenReturn(1L);

        ResponseEntity<Void> response = controller.enrollInCourse(100L, mockUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(enrollmentService).enrollToCourse(1L, 100L);
    }
}
