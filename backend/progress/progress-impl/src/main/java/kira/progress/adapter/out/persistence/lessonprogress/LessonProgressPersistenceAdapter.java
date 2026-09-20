package kira.progress.adapter.out.persistence.lessonprogress;

import common.domain.Id;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import kira.progress.domain.lessonprogress.LessonProgress;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LessonProgressPersistenceAdapter implements LessonProgressSavePort, LessonProgressLoadPort {
    private final DataLessonProgressRepository repository;
    private final LessonProgressMapper mapper;

    @Override
    public boolean isLessonCompleted(Id<User> userId, Id<Lesson> lessonId) {
        var id = new LessonProgressEntity.LessonProgressKey(userId.value(), lessonId.value());
        return repository.existsById(id);
    }

    @Override
    public void save(LessonProgress lessonProgress) {
        LessonProgressEntity entity = mapper.toEntity(lessonProgress);
        repository.save(entity);
    }
}
