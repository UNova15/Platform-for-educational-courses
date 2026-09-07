package refactor.course.application.service.factory;

import java.util.List;

import refactor.common.domain.Id;
import refactor.course.application.port.in.course.create.CourseCreateCommand;
import refactor.course.domain.common.Description;
import refactor.course.domain.common.Title;
import refactor.course.domain.course.Course;
import refactor.course.domain.module.CourseModule;
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
