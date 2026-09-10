package refactor.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.progress.application.exception.CourseAccessDenialException;
import refactor.progress.application.exception.LessonNotFoundException;
import refactor.progress.application.port.in.lessonprogress.LessonCompletionUseCase;
import refactor.progress.application.port.out.external.LessonProviderPort;
import refactor.progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import refactor.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import refactor.progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import refactor.progress.domain.lessonprogress.LessonProgress;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

@Service
@RequiredArgsConstructor
public class LessonCompletionService implements LessonCompletionUseCase {
    private final LessonProviderPort lessonProviderPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final LessonProgressLoadPort lessonProgressLoadPort;
    private final LessonProgressSavePort lessonProgressSavePort;

    @Override
    public void completeLesson(Id<User> userId, Id<Lesson> lessonId) {
        Id<Course> courseId = lessonProviderPort
                .findCourseIdByLessonId(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new CourseAccessDenialException(courseId, userId);
        }

        if (lessonProgressLoadPort.isLessonCompleted(userId, lessonId)) {
            return;
        }

        LessonProgress progress = LessonProgress.createNew(userId, lessonId);
        lessonProgressSavePort.save(progress);
    }
}
