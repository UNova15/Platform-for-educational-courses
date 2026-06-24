package org.platform.platformforeducationalcourses.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.teacher.TeacherLessonController;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.platform.platformforeducationalcourses.service.domain.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TeacherLessonControllerTest {

    @Mock
    private LessonService lessonService;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private TeacherLessonController controller;

    private SecurityUser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(SecurityUser.class);
        lenient().when(mockUser.getId()).thenReturn(1L);
    }

    @Test
    void createLesson_ReturnsCreated() {
        CourseLessonCreateRequest request = mock(CourseLessonCreateRequest.class);
        LessonCreateDto dto = mock(LessonCreateDto.class);
        LessonCreateResponse expectedResponse = mock(LessonCreateResponse.class);

        when(lessonMapper.toLessonCreateDto(200L, request)).thenReturn(dto);
        when(lessonService.createLesson(dto)).thenReturn(expectedResponse);

        ResponseEntity<LessonCreateResponse> response = controller.createLesson(100L, 200L, request, mockUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void updateLesson_ReturnsNoContent() {
        LessonUpdateRequest request = mock(LessonUpdateRequest.class);
        LessonUpdateDto dto = mock(LessonUpdateDto.class);

        when(lessonMapper.toLessonUpdateDto(300L, 200L, request)).thenReturn(dto);

        ResponseEntity<Void> response = controller.updateLesson(100L, 200L, 300L, request, mockUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(lessonService).updateLesson(dto);
    }

    @Test
    void deleteLesson_ReturnsNoContent() {
        ResponseEntity<Void> response = controller.deleteLesson(100L, 200L, 300L, mockUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(lessonService).deleteLesson(300L);
    }

    @Test
    void getLesson_ReturnsOk() {
        LessonFindResponse expectedResponse = mock(LessonFindResponse.class);
        when(lessonService.findLesson(300L)).thenReturn(expectedResponse);

        ResponseEntity<LessonFindResponse> response = controller.getLesson(100L, 200L, 300L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}
