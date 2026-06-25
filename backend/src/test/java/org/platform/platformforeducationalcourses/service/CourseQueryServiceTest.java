package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.creator.assembler.ModuleAssembler;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.platform.platformforeducationalcourses.service.domain.ProgressService;
import org.platform.platformforeducationalcourses.service.domain.TestSubmissionService;

@ExtendWith(MockitoExtension.class)
class CourseQueryServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private TestRepository testRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private ModuleAssembler moduleAssembler;

    @Mock
    private ProgressService progressService;

    @Mock
    private TestSubmissionService testSubmissionService;

    @InjectMocks
    private CourseQueryService service;

    @Test
    void getCourseForCatalog_Success() {
        Course course = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(moduleRepository.findAllByCourseId(1L)).thenReturn(List.of());

        CourseCatalogResponse expectedResponse = mock(CourseCatalogResponse.class);
        when(courseMapper.toCourseCatalogResponse(eq(course), anyList())).thenReturn(expectedResponse);

        CourseCatalogResponse response = service.getCourseForCatalog(1L);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getCourseForCatalog_ThrowsException_IfNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.getCourseForCatalog(1L));
    }

    @Test
    void getCourseForTeacher_Success_CallsPrivateMethod() {
        Course course = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        service.getCourseForTeacher(1L);

        verify(moduleRepository).findAllByCourseId(1L);
        verify(lessonRepository).findAllByModuleIdIn(anyList());
        verify(testRepository).findAllByModuleIdIn(anyList());
        verify(moduleAssembler).createCourseModuleFindResponse(any(), any(), any());
    }
}
