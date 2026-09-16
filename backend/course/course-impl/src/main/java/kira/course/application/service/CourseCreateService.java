package kira.course.application.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import common.domain.Id;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.application.port.in.course.create.CourseCreateResult;
import kira.course.application.port.in.course.create.CourseCreateUseCase;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.application.service.factory.LessonFactory;
import kira.course.application.service.factory.ModuleFactory;
import kira.course.application.service.factory.TestFactory;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// В текущем варианте валидный курс обязательно содержит 1 модуль и 1 урок. В будущем введется понятие статуса
// курса (DRAFT,PUBLISHED, ARCHIVED). Тогда доменный класс Course будет иметь метод publish и принимать список курсов и
// список уроков для контроля своего инварианта

@Service
@AllArgsConstructor
public class CourseCreateService implements CourseCreateUseCase {
    private final CourseSavePort courseSavePort;

    private final ModuleSavePort moduleSavePort;
    private final LessonSavePort lessonSavePort;
    private final TestSavePort testSavePort;

    private final ModuleFactory moduleFactory;
    private final LessonFactory lessonFactory;
    private final TestFactory testFactory;

    @Override
    @Transactional
    public CourseCreateResult createCourseWithContent(CourseCreateCommand command, Id<User> userId) {
        Course course = Course.createNew(
                userId, Title.of(command.title()), Description.of(command.description()), command.tag());
        Course savedCourse = courseSavePort.save(course);

        List<CourseModule> modules = moduleFactory.fromCommand(command.modules(), savedCourse.id());
        List<CourseModule> savedModules = moduleSavePort.saveAll(modules);

        Map<Integer, Id<CourseModule>> orderedModules =
                savedModules.stream().collect(Collectors.toMap(CourseModule::orderIndex, CourseModule::id));

        List<Lesson> lessonsToSave = command.modules().stream()
                .filter(module -> module.lessons() != null && !module.lessons().isEmpty())
                .map(module -> lessonFactory.fromLessonCreateCommand(
                        module.lessons(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        List<Test> testToSave = command.modules().stream()
                .filter(module -> module.tests() != null && !module.tests().isEmpty())
                .map(module ->
                        testFactory.fromCourseCreateCommand(module.tests(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        lessonSavePort.saveAll(lessonsToSave);
        testSavePort.saveAll(testToSave);

        return new CourseCreateResult(savedCourse.id().value(), command.title(), savedCourse.createdAt());
    }
}
