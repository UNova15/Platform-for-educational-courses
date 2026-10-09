package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResponse;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonCreateService implements LessonCreateUseCase {
    private final LessonSavePort savePort;

    private final CourseStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    @Override
    public Result<LessonCreateResponse, CreateErrors> createLesson(
            LessonCreateCommand createCommand, Id<User> teacherId, Id<CourseModule> moduleId) {

        if (!moduleLoadPort.isExistsModule(moduleId)) {
            return Result.failure(CreateErrors.MODULE_NOT_FOUND);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            return Result.failure(CreateErrors.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByModuleId(moduleId)) {
            return Result.failure(CreateErrors.COURSE_ARCHIVED);
        }

        var title = Title.of(createCommand.title());
        var content = LessonContent.of(createCommand.type(), createCommand.content());
        Lesson lesson = Lesson.create(moduleId, title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);

        var response = new LessonCreateResponse(
                savedLesson.id().value(), moduleId.value(), savedLesson.title().value());

        return Result.success(response);
    }
}
