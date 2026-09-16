package kira.course.application.service;

import common.domain.Id;
import common.exception.DomainModificationException;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResult;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.in.lesson.update.LessonUpdateCommand;
import kira.course.application.port.in.lesson.update.LessonUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.domain.common.Title;
import kira.course.domain.lesson.Content;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            LessonCreateCommand createCommand, Id<User> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistModule(moduleId)) {
            throw new ResourceNotFoundException(
                    CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.MODULE_ACCESS_EXCEPTION, CourseModule.class, teacherId, moduleId);
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
    public void removeLesson(Id<User> teacherId, Id<Lesson> lessonId) {
        if (!loadPort.isExist(lessonId)) {
            throw new ResourceNotFoundException(CourseExceptionCode.LESSON_NOT_FOUND_EXCEPTION, Lesson.class, lessonId);
        }

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.LESSON_ACCESS_EXCEPTION, Lesson.class, teacherId, lessonId);
        }

        removePort.removeById(lessonId);
    }

    @Override
    public void updateLesson(LessonUpdateCommand updateCommand, Id<Lesson> lessonId, Id<User> teacherId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.LESSON_NOT_FOUND_EXCEPTION, Lesson.class, lessonId));

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.LESSON_ACCESS_EXCEPTION, Lesson.class, teacherId, lessonId);
        }

        var title = Title.of(updateCommand.title());
        var content = Content.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        savePort.save(lesson);
    }
}
