package kira.course.application.service.core.lesson;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResponse;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.domain.CourseAggregateType;
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

    private final CourseDetailsProviderPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    @Override
    public LessonCreateResponse createLesson(
            LessonCreateCommand createCommand, Id<User> teacherId, Id<CourseModule> moduleId) {

        if (!moduleLoadPort.isExistsModule(moduleId)) {
            throw new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, teacherId, moduleId);
        }

        CourseDetails details = checkStatusPort.findCourseDetailsByModuleId(moduleId);
        details.verifyCanChangeContent();

        var title = Title.of(createCommand.title());
        var content = LessonContent.of(createCommand.type(), createCommand.content());
        Lesson lesson = Lesson.create(moduleId, title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);

        return new LessonCreateResponse(
                savedLesson.id().value(), moduleId.value(), savedLesson.title().value());
    }
}
