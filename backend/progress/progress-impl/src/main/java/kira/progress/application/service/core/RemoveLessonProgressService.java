package kira.progress.application.service.core;

import common.domain.entity.Id;
import kira.progress.application.port.in.lessonprogress.RemoveLessonProgressUseCase;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressRemovePort;
import kira.progress.domain.markers.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RemoveLessonProgressService implements RemoveLessonProgressUseCase {
    private final LessonProgressRemovePort removePort;

    @Override
    public void removeLessonProgress(Id<Lesson> lessonId) {
        removePort.removeAllByLessonId(lessonId);
    }
}
