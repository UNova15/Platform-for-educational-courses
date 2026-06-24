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
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.module.ModuleCreateDto;
import org.platform.platformforeducationalcourses.dto.module.ModuleUpdateDto;
import org.platform.platformforeducationalcourses.mapper.ModuleMapper;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.service.domain.ModuleService;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private ModuleMapper moduleMapper;

    @InjectMocks
    private ModuleService moduleService;

    @Test
    void findModule_Success() {
        CourseModule module = mock(CourseModule.class);
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        moduleService.findModule(1L);

        verify(moduleMapper).toModuleFindResponse(module);
    }

    @Test
    void updateModule_Success() {
        CourseModule module = mock(CourseModule.class);
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        ModuleUpdateDto dto = mock(ModuleUpdateDto.class);
        when(dto.moduleId()).thenReturn(1L);
        when(dto.title()).thenReturn("New Title");

        moduleService.updateModule(dto);

        verify(module).updateInfo(anyString(), any(), anyInt());
        verify(moduleRepository).save(module);
    }

    @Test
    void deleteModule_Success() {
        CourseModule module = mock(CourseModule.class);
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        moduleService.deleteModule(1L);

        verify(moduleRepository).delete(module);
    }

    @Test
    void createModule_Success() {
        ModuleCreateDto dto = mock(ModuleCreateDto.class);
        when(dto.courseId()).thenReturn(1L);
        when(dto.title()).thenReturn("Title");

        CourseModule savedModule = mock(CourseModule.class);
        when(moduleRepository.save(any(CourseModule.class))).thenReturn(savedModule);

        moduleService.createModule(dto);

        verify(moduleMapper).toCreateModuleResponse(savedModule);
    }

    @Test
    void generic_ThrowsException_IfNotFound() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> moduleService.findModule(1L));
        assertThrows(NoSuchElementException.class, () -> moduleService.deleteModule(1L));
    }
}
