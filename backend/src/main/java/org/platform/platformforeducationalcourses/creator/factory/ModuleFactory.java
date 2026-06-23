package org.platform.platformforeducationalcourses.creator.factory;


import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseModuleCreateDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Фабрика для создания доменных объектов Module из dto
 */
@Component
public class ModuleFactory {
    public List<CourseModule> createFromCreateDto(List<CourseModuleCreateDto> modules, long courseId) {
        return modules.stream()
                .map(module -> CourseModule.createNew(courseId, module.title(), module.description(), module.orderIndex()))
                .toList();
    }
}
