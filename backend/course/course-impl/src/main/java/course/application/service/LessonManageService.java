package course.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.common.exception.access.LessonAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.DomainModificationException;
import refactor.common.exception.domain.LessonNotFoundException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.course.implementation.application.port.in.lesson.create.LessonCreateCommand;
import refactor.course.implementation.application.port.in.lesson.create.LessonCreateResult;
import refactor.course.implementation.application.port.in.lesson.create.LessonCreateUseCase;
import refactor.course.implementation.application.port.in.lesson.remove.LessonRemoveUseCase;
import refactor.course.implementation.application.port.in.lesson.update.LessonUpdateCommand;
import refactor.course.implementation.application.port.in.lesson.update.LessonUpdateUseCase;
import refactor.course.implementation.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.implementation.application.port.out.persistance.lesson.LessonLoadPort;
import refactor.course.implementation.application.port.out.persistance.lesson.LessonRemovePort;
import refactor.course.implementation.application.port.out.persistance.lesson.LessonSavePort;
import refactor.course.implementation.application.port.out.persistance.module.ModuleLoadPort;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.lesson.Content;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.markers.Account;

@Service
@RequiredArgsConstructor
public class LessonManageService implements LessonCreateUseCase, LessonRemoveUseCase, LessonUpdateUseCase {
    private final LessonSavePort savePort;
    private final CourseAccessPort accessPort;
    private final LessonLoadPort loadPort;
    private final LessonRemovePort removePort;
    private final ModuleLoadPort moduleLoadPort;

    @Override
    public LessonCreateResult createLesson(
            LessonCreateCommand createCommand, Id<Account> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistModule(moduleId)) {
            throw new ModuleNotFoundException(moduleId, teacherId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }

        // не смог сделать данную проверку через доменный класс
        if (loadPort.countByModuleId(moduleId) >= CourseModule.MAX_LESSONS_COUNT) {
            throw new DomainModificationException(
                    "The maximum number of lessons in the module with ID: %d has been exceeded"
                            .formatted(moduleId.value()));
        }

        var title = Title.of(createCommand.title());
        var content = Content.of(createCommand.type(), createCommand.content());

        Lesson lesson =
                Lesson.createNew(moduleId, title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);
        return new LessonCreateResult(
                savedLesson.id().value(), moduleId.value(), savedLesson.title().value());
    }

    @Override
    public void removeLesson(Id<Account> teacherId, Id<Lesson> lessonId) {
        if (!loadPort.isExist(lessonId)) {
            throw new LessonNotFoundException(lessonId);
        }

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new LessonAccessException(lessonId, teacherId);
        }

        removePort.removeById(lessonId);
    }

    @Override
    public void updateLesson(LessonUpdateCommand updateCommand, Id<Lesson> lessonId, Id<Account> teacherId) {
        Lesson lesson = loadPort.loadLessonById(lessonId).orElseThrow(() -> new LessonNotFoundException(lessonId));

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new LessonAccessException(lessonId, teacherId);
        }

        var title = Title.of(updateCommand.title());
        var content = Content.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        savePort.save(lesson);
    }
}
