package refactor.course.application.service.factory;

import java.util.List;

import refactor.common.domain.Id;
import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.domain.internal.common.Description;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.module.CourseModule;
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
