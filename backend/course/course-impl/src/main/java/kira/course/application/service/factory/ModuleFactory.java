package kira.course.application.service.factory;

import java.util.List;

import common.domain.Id;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
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
