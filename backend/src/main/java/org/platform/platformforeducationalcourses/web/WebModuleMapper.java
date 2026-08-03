package org.platform.platformforeducationalcourses.web;

import java.util.List;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.course.create.ModuleCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class WebModuleMapper {
    public List<CourseModule> fromRequest(List<ModuleCreateRequest> modules, long courseId) {
        return modules.stream()
                .map(module ->
                        CourseModule.createNew(courseId, module.title(), module.description(), module.orderIndex()))
                .toList();
    }
}
