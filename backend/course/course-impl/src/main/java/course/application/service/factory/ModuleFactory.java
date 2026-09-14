package course.application.service.factory;

import java.util.List;

import refactor.common.domain.Id;
import refactor.course.implementation.application.port.in.course.create.CourseCreateCommand;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.module.CourseModule;
import org.springframework.stereotype.Component;

@Component
public class ModuleFactory {
    public List<CourseModule> fromCommand(List<CourseCreateCommand.ModuleCommand> modules, Id<Course> courseId) {
        return modules.stream()
                .map(module -> CourseModule.createNew(
                        courseId, Title.of(module.title()), Description.of(module.description()), module.orderIndex()))
                .toList();
    }
}
