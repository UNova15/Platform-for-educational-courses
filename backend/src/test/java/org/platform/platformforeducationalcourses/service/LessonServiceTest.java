package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.domain.course.ContentType;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.lesson.LessonCreateDto;
import org.platform.platformforeducationalcourses.dto.lesson.LessonUpdateDto;
import org.platform.platformforeducationalcourses.exception.LessonNotFoundException;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.service.domain.LessonService;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void deleteLesson_Success() {
        Lesson mockLesson = mock(Lesson.class);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(mockLesson));

        lessonService.deleteLesson(1L);

        verify(lessonRepository).delete(mockLesson);
    }

    @Test
    void updateLesson_Success() {
        Lesson mockLesson = mock(Lesson.class);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(mockLesson));

        LessonUpdateDto dto = mock(LessonUpdateDto.class);
        when(dto.lessonId()).thenReturn(1L);
        when(dto.title()).thenReturn("T");
        when(dto.type()).thenReturn(ContentType.VIDEO);
        when(dto.content()).thenReturn("C");

        lessonService.updateLesson(dto);

        verify(mockLesson).update(anyString(), any(), anyString(), anyInt(), anyBoolean());
        verify(lessonRepository).save(mockLesson);
    }

    @Test
    void updateLesson_ThrowsException_IfNotFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        LessonUpdateDto dto = mock(LessonUpdateDto.class);
        when(dto.lessonId()).thenReturn(1L);

        assertThrows(NoSuchElementException.class, () -> lessonService.updateLesson(dto));
    }

    @Test
    void createLesson_Success() {
        LessonCreateDto dto = mock(LessonCreateDto.class);
        when(dto.moduleId()).thenReturn(1L);
        when(dto.title()).thenReturn("T");
        when(dto.type()).thenReturn(ContentType.VIDEO);
        when(dto.content()).thenReturn("C");

        Lesson savedLesson = mock(Lesson.class);
        when(savedLesson.getId()).thenReturn(100L);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(savedLesson);

        lessonService.createLesson(dto);

        verify(lessonMapper).toLessonCreateResponse(100L, dto);
    }

    @Test
    void findLesson_ThrowsException_IfNotFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> lessonService.findLesson(1L));
    }
}
