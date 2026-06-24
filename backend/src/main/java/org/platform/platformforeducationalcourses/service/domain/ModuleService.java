package org.platform.platformforeducationalcourses.service.domain;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.module.ModuleCreateDto;
import org.platform.platformforeducationalcourses.dto.module.ModuleCreateResponse;
import org.platform.platformforeducationalcourses.dto.module.ModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.module.ModuleUpdateDto;
import org.platform.platformforeducationalcourses.mapper.ModuleMapper;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    public ModuleFindResponse findModule(long moduleId) {
        CourseModule module = moduleRepository.findById(moduleId).orElseThrow();
        return moduleMapper.toModuleFindResponse(module);
    }

    public void updateModule(ModuleUpdateDto updateDto) {
        CourseModule module = moduleRepository.findById(updateDto.moduleId()).orElseThrow();

        module.updateInfo(updateDto.title(), updateDto.description(), updateDto.order_index());
        moduleRepository.save(module);
    }

    public void deleteModule(long moduleId) {
        CourseModule courseModule = moduleRepository.findById(moduleId).orElseThrow();

        moduleRepository.delete(courseModule);
    }

    public ModuleCreateResponse createModule(ModuleCreateDto moduleDto) {
        CourseModule module = CourseModule.createNew(
                moduleDto.courseId(), moduleDto.title(), moduleDto.description(), moduleDto.orderIndex());

        CourseModule savedCourse = moduleRepository.save(module);

        return moduleMapper.toCreateModuleResponse(savedCourse);
    }
}
