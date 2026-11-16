package kira.course.application.service.core.lesson;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.events.ContentChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class LessonRemoveService implements LessonRemoveUseCase {
    private final CourseAccessPort accessPort;
    private final LessonLoadPort loadPort;
    private final LessonRemovePort removePort;
    private final CourseDetailsProviderPort checkStatusPort;

    private final EventPublisherPort publisherPort;

    @Override
    @Transactional
    public void removeLesson(Id<User> teacherId, Id<Lesson> lessonId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.LESSON, lessonId));

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.LESSON, teacherId, lessonId);
        }

        CourseDetails details = checkStatusPort.findCourseDetailsByLessonId(lessonId);
        details.verifyCanChangeContent();

        removePort.removeById(lessonId);

        publisherPort.publish(new ContentChangeEvent(
                ContentChangeEvent.ContentChangeType.LESSON,
                ContentChangeEvent.ChangeAction.REMOVE,
                lesson.title().value(),
                lesson.id().value(),
                lesson.isPublish()));
    }
}
