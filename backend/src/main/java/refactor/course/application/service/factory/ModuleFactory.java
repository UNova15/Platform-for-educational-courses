package refactor.course.application.service.factory;

import java.util.List;

import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.domain.module.CourseModule;
import org.springframework.stereotype.Component;
import refactor.course.domain.module.ModuleDescription;
import refactor.course.domain.module.ModuleTitle;

@Component
public class ModuleFactory {
    public List<CourseModule> fromCommand(List<CourseCreateCommand.ModuleCommand> modules, long courseId) {
        return modules.stream()
                .map(module -> CourseModule.createNew(
                        courseId,
                        ModuleTitle.of(module.title()),
                        ModuleDescription.of(module.description()),
                        module.orderIndex()))
                .toList();
    }
}
