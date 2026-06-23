package org.platform.platformforeducationalcourses.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.teacher.TeacherCoursesController;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.*;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateRequest;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseCreateDto;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.service.CourseManagementService;
import org.platform.platformforeducationalcourses.service.CourseQueryService;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherCoursesControllerTest {

    @Mock private CourseQueryService courseQueryService;
    @Mock private CourseManagementService courseManagementService;
    @Mock private CourseService courseService;
    @Mock private CourseMapper courseMapper;

    @InjectMocks private TeacherCoursesController controller;

    private SecurityUser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(SecurityUser.class);
        lenient().when(mockUser.getId()).thenReturn(1L);
    }

    @Test
    void createCourse_ReturnsCreated() {
        CourseCreateRequest request = mock(CourseCreateRequest.class);
        CourseCreateDto dto = mock(CourseCreateDto.class);
        CourseCreateResponse expectedResponse = mock(CourseCreateResponse.class);

        when(courseMapper.toCourseDto(request)).thenReturn(dto);
        when(courseManagementService.createCourseWithContent(dto, 1L)).thenReturn(expectedResponse);

        ResponseEntity<CourseCreateResponse> response = controller.createCourse(mockUser, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void updateCourse_ReturnsNoContent() {
        CourseUpdateRequest request = mock(CourseUpdateRequest.class);
        CourseUpdateDto dto = mock(CourseUpdateDto.class);

        when(courseMapper.toCourseUpdateDto(request, 1L, 100L)).thenReturn(dto);

        ResponseEntity<Void> response = controller.updateCourse(100L, request, mockUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService).updateCourse(dto, 1L, 100L);
    }

    @Test
    void deleteCourse_ReturnsNoContent() {
        ResponseEntity<Void> response = controller.deleteCourse(100L, mockUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService).deleteCourse(1L, 100L);
    }

    @Test
    void getCourses_ReturnsOk() {
        List<CourseInfo> expectedList = List.of();
        when(courseService.findTeachersCoursesInfo(1L)).thenReturn(expectedList);

        ResponseEntity<List<CourseInfo>> response = controller.getCourses(mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void getCourse_ReturnsOk() {
        CourseFindResponse expectedResponse = mock(CourseFindResponse.class);
        when(courseQueryService.getCourseForTeacher(100L)).thenReturn(expectedResponse);

        ResponseEntity<CourseFindResponse> response = controller.getCourse(100L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}