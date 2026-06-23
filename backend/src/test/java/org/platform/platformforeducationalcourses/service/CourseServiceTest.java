package org.platform.platformforeducationalcourses.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.creator.assembler.CourseAssembler;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.course.CourseUpdateDto;
import org.platform.platformforeducationalcourses.exception.CourseNotFoundException;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock private CourseRepository courseRepository;
    @Mock private CourseAssembler courseAssembler;
    @Mock private CourseMapper courseMapper;

    @InjectMocks private CourseService courseService;

    @Test
    void updateCourse_Success() {
        Course mockCourse = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));

        CourseUpdateDto updateDto = mock(CourseUpdateDto.class);

        courseService.updateCourse(updateDto, 10L, 1L);

        verify(mockCourse).updateCourse(updateDto);
        verify(courseRepository).save(mockCourse);
    }

    @Test
    void updateCourse_ThrowsException_IfNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(mock(CourseUpdateDto.class), 10L, 1L));
    }

    @Test
    void deleteCourse_Success() {
        Course mockCourse = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));

        courseService.deleteCourse(10L, 1L);

        verify(courseRepository).delete(mockCourse);
    }

    @Test
    void findTeachersCoursesInfo_Success() {
        Course mockCourse = mock(Course.class);
        when(courseRepository.findAllByTeacherId(10L)).thenReturn(List.of(mockCourse));

        courseService.findTeachersCoursesInfo(10L);

        verify(courseMapper, times(1)).toCourseGetResponse(mockCourse);
    }

    @Test
    void findPageOfCourse_WithoutTag_Success() {
        Pageable pageable = mock(Pageable.class);
        Page<Course> mockPage = mock(Page.class);
        when(courseRepository.findAll(pageable)).thenReturn(mockPage);

        courseService.findPageOfCourse(pageable, null);

        verify(courseAssembler).createPageResponseWithCoursePage(mockPage);
        verify(courseRepository, never()).findAllByTag(any(), any());
    }

    @Test
    void findPageOfCourse_WithTag_Success() {
        Pageable pageable = mock(Pageable.class);
        Tag tag = mock(Tag.class);
        Page<Course> mockPage = mock(Page.class);
        when(courseRepository.findAllByTag(pageable, tag)).thenReturn(mockPage);

        courseService.findPageOfCourse(pageable, tag);

        verify(courseAssembler).createPageResponseWithCoursePage(mockPage);
        verify(courseRepository, never()).findAll(any(Pageable.class));
    }
}