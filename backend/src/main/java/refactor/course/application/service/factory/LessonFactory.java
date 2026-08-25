package refactor.course.application.service.factory;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.common.domain.Id;
import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.internal.lesson.Content;
import refactor.course.domain.internal.lesson.Lesson;
import org.springframework.stereotype.Component;
import refactor.course.domain.internal.module.CourseModule;

@Component
@AllArgsConstructor
public class LessonFactory {
    public List<Lesson> fromLessonCreateCommand(List<CourseCreateCommand.LessonCommand> lessons, Id<CourseModule> moduleId) {
        return lessons.stream()
                .map(lesson -> fromLessonCreateCommand(lesson, moduleId))
                .toList();
    }

    public Lesson fromLessonCreateCommand(CourseCreateCommand.LessonCommand lesson, Id<CourseModule> moduleId) {
        return Lesson.createNew(
                moduleId,
                Title.of(lesson.title()),
                Content.of(lesson.type(), lesson.content()),
                lesson.orderIndex(),
                lesson.mandatory());
    }
}
