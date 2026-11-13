package kira.progress.adapter.out.persistence.lessonprogress;

import common.domain.entity.Id;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressRemovePort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import kira.progress.domain.lessonprogress.LessonProgress;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LessonProgressPersistenceAdapter
        implements LessonProgressSavePort, LessonProgressLoadPort, LessonProgressRemovePort {
    private final DataLessonProgressRepository repository;
    private final LessonProgressMapper mapper;

    @Override
    public boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId) {
        return repository.existsByUserIdAndLessonId(userId.value(), lessonId.value());
    }

    @Override
    public void save(LessonProgress lessonProgress) {
        LessonProgressEntity entity = mapper.toEntity(lessonProgress);
        repository.save(entity);
    }

    @Override
    public void removeAllByLessonId(Id<Lesson> lessonId) {
        repository.removeAllByLessonId(lessonId.value());
    }
}
