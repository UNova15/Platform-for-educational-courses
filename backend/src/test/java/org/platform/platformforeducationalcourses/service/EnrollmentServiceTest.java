package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.domain.progress.Enrollment;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.EnrollmentRepository;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private EnrollmentService service;

    @Test
    void enrollToCourse_Success() {
        when(enrollmentRepository.findByCourseIdAndUserId(2L, 1L)).thenReturn(Optional.empty());

        service.enrollToCourse(1L, 2L);

        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void enrollToCourse_ThrowsException_WhenAlreadyEnrolled() {
        Enrollment existingEnrollment = mock(Enrollment.class);
        when(enrollmentRepository.findByCourseIdAndUserId(2L, 1L)).thenReturn(Optional.of(existingEnrollment));

        assertThrows(IllegalArgumentException.class, () -> service.enrollToCourse(1L, 2L));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void getCoursesForStudent_Success() {
        Enrollment enrollment = mock(Enrollment.class);
        when(enrollment.getCourseId()).thenReturn(10L);

        when(enrollmentRepository.findEnrollmentByUserId(1L)).thenReturn(List.of(enrollment));
        when(courseRepository.findCoursesByIdIn(List.of(10L))).thenReturn(List.of());

        List<CourseEnrolledFindResponse> expectedResponses = List.of(mock(CourseEnrolledFindResponse.class));
        when(courseMapper.toCourseEnrolledFindResponse(anyList())).thenReturn(expectedResponses);

        List<CourseEnrolledFindResponse> actualResponses = service.getCoursesForStudent(1L);

        assertEquals(expectedResponses, actualResponses);
        verify(courseRepository).findCoursesByIdIn(List.of(10L));
    }
}
