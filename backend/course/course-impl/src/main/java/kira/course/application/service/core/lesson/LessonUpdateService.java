package kira.course.application.service.core.lesson;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.lesson.update.LessonUpdateCommand;
import kira.course.application.port.in.lesson.update.LessonUpdateUseCase;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Title;
import kira.course.events.ContentChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class LessonUpdateService implements LessonUpdateUseCase {
    private final LessonLoadPort loadPort;
    private final LessonSavePort savePort;

    private final CourseAccessPort accessPort;
    private final CourseDetailsProviderPort checkStatusPort;

    private final EventPublisherPort publisherPort;

    @Override
    @Transactional
    public void updateLesson(LessonUpdateCommand updateCommand, Id<Lesson> lessonId, Id<User> teacherId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.LESSON, lessonId));

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.LESSON, teacherId, lessonId);
        }

        CourseDetails details = checkStatusPort.findCourseDetailsByLessonId(lessonId);
        details.verifyCanChangeContent();

        var title = Title.of(updateCommand.title());
        var content = LessonContent.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        savePort.save(lesson);

        publisherPort.publish(new ContentChangeEvent(
                ContentChangeEvent.ContentChangeType.LESSON,
                ContentChangeEvent.ChangeAction.UPDATE,
                lesson.title().value(),
                lesson.id().value(),
                lesson.isPublish()));
    }
}
