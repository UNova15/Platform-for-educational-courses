package kira.progress.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.progress.application.exceptions.ProgressExceptionCode;
import kira.progress.application.port.out.external.CourseStructurePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import kira.progress.application.port.in.lessonprogress.LessonCompletionUseCase;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import kira.progress.domain.lessonprogress.LessonProgress;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;

@Service
@RequiredArgsConstructor
public class LessonCompletionService implements LessonCompletionUseCase {
    private final CourseStructurePort courseStructurePort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final LessonProgressLoadPort lessonProgressLoadPort;
    private final LessonProgressSavePort lessonProgressSavePort;

    @Override
    public void completeLesson(Id<User> userId, Id<Lesson> lessonId) {
        Id<Course> courseId = courseStructurePort
                .findCourseIdByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.LESSON_NOT_FOUND_EXCEPTION, Lesson.class, lessonId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, userId, courseId);
        }

        if (lessonProgressLoadPort.isLessonCompleted(userId, lessonId)) {
            return;
        }

        LessonProgress progress = LessonProgress.createNew(userId, lessonId);
        lessonProgressSavePort.save(progress);
    }
}
