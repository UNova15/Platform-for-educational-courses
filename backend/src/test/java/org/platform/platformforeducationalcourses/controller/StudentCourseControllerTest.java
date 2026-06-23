package org.platform.platformforeducationalcourses.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.student.StudentCourseController;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.StudentCourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestPostRequest;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.service.CourseLearningService;
import org.platform.platformforeducationalcourses.service.CourseQueryService;
import org.platform.platformforeducationalcourses.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCourseControllerTest {

    @Mock private CourseLearningService learningService;
    @Mock private CourseQueryService courseQueryService;
    @Mock private EnrollmentService enrollmentService;

    @InjectMocks private StudentCourseController controller;

    private SecurityUser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(SecurityUser.class);
        when(mockUser.getId()).thenReturn(1L);
    }

    @Test
    void getCourses_ReturnsOk() {
        List<CourseEnrolledFindResponse> expectedList = List.of();
        when(enrollmentService.getCoursesForStudent(1L)).thenReturn(expectedList);

        ResponseEntity<List<CourseEnrolledFindResponse>> response = controller.getCourses(mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void getCourse_ReturnsOk() {
        StudentCourseFindResponse expectedResponse = mock(StudentCourseFindResponse.class);
        when(courseQueryService.getCourseForStudent(1L, 100L)).thenReturn(expectedResponse);

        ResponseEntity<StudentCourseFindResponse> response = controller.getCourse(100L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getLesson_ReturnsOk() {
        LessonFindResponse expectedResponse = mock(LessonFindResponse.class);
        when(learningService.getLesson(1L, 300L)).thenReturn(expectedResponse);

        ResponseEntity<LessonFindResponse> response = controller.getLesson(100L, 200L, 300L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getTestReview_ReturnsOk() {
        TestReview expectedResponse = mock(TestReview.class);
        when(learningService.getTestReview(400L, 1L)).thenReturn(expectedResponse);

        ResponseEntity<TestReview> response = controller.getTestReview(100L, 200L, 400L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void startTestAttempt_ReturnsOk() {
        TestFindResponse expectedResponse = mock(TestFindResponse.class);
        when(learningService.startAttempt(400L, 1L)).thenReturn(expectedResponse);

        ResponseEntity<TestFindResponse> response = controller.startTestAttempt(100L, 200L, 400L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void postTest_ReturnsCreated() {
        TestPostRequest request = mock(TestPostRequest.class);

        ResponseEntity<Void> response = controller.postTest(400L, 100L, 200L, request, mockUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(learningService).endAttempt(request, 1L, 400L);
    }
}