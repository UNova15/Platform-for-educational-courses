package org.platform.platformforeducationalcourses.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.creator.factory.LessonFactory;
import org.platform.platformforeducationalcourses.creator.factory.ModuleFactory;
import org.platform.platformforeducationalcourses.creator.factory.TestFactory;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseCreateDto;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseModuleCreateDto;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseManagementServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ModuleRepository moduleRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private ModuleFactory moduleFactory;
    @Mock
    private LessonFactory lessonFactory;
    @Mock
    private TestFactory testFactory;

    @InjectMocks
    private CourseManagementService service;

    @Test
    void createCourseWithContent_Success() {
        CourseModuleCreateDto moduleDto = mock(CourseModuleCreateDto.class);
        when(moduleDto.orderIndex()).thenReturn(1);

        CourseCreateDto requestDto = mock(CourseCreateDto.class);
        when(requestDto.title()).thenReturn("Test Course");
        when(requestDto.modules()).thenReturn(List.of(moduleDto));

        Course savedCourse = mock(Course.class);
        when(savedCourse.getId()).thenReturn(100L);
        when(courseRepository.save(any())).thenReturn(savedCourse);

        CourseModule savedModule = mock(CourseModule.class);
        when(savedModule.getId()).thenReturn(200L);
        when(savedModule.getOrderIndex()).thenReturn(1);
        when(moduleRepository.saveAll(any())).thenReturn(List.of(savedModule));

        CourseCreateResponse response = service.createCourseWithContent(requestDto, 1L);

        assertEquals(100L, response.courseId());
        assertEquals("Test Course", response.title());

        verify(lessonRepository).saveAll(any());
        verify(testRepository).saveAll(any());
    }
}