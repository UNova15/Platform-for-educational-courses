package kira.course.application.service.factory;

import java.util.List;

import common.domain.Id;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.domain.common.Title;
import kira.course.domain.lesson.Content;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
