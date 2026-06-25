package org.platform.platformforeducationalcourses.service.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.repository.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProgressService {
    private final ProgressRepository progressRepository;

    public List<LessonProgress> findLessonProgressByLessonsIds(long userId, List<Lesson> lessons) {
        List<Long> lessonsIds = lessons.stream().map(Lesson::getId).toList();
        return progressRepository.findAllByUserIdAndLessonIdIn(userId, lessonsIds);
    }
}
