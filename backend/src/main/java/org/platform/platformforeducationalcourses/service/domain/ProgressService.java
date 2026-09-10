package org.platform.platformforeducationalcourses.service.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.lesson.Lesson;
import refactor.progress.domain.lessonprogress.LessonProgress;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataProgressRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProgressService {
    private final DataProgressRepository progressRepository;

    public List<LessonProgress> findLessonProgressByLessonsIds(long userId, List<Lesson> lessons) {
        List<Long> lessonsIds = lessons.stream().map(Lesson::getId).toList();
        return progressRepository.findAllByUserIdAndLessonIdIn(userId, lessonsIds);
    }
}
