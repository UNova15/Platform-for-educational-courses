package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResponse;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LessonCreateService implements LessonCreateUseCase {
    private final LessonSavePort savePort;

    private final CourseStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    @Override
    public LessonCreateResponse createLesson(
            LessonCreateCommand createCommand, Id<User> teacherId, Id<CourseModule> moduleId) {

        if (!moduleLoadPort.isExistsModule(moduleId)) {
            throw new ResourceNotFoundException(CourseResourceName.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, teacherId, moduleId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByModuleId(moduleId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException("Cannot create lesson in archived course")
                    .withContext("moduleId", moduleId.value());
        }

        var title = Title.of(createCommand.title());
        var content = LessonContent.of(createCommand.type(), createCommand.content());
        Lesson lesson = Lesson.create(moduleId, title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);

        return new LessonCreateResponse(
                savedLesson.id().value(), moduleId.value(), savedLesson.title().value());
    }
}
