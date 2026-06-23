package org.platform.platformforeducationalcourses.creator.assembler;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LessonAssembler {
    private final LessonMapper lessonMapper;

    public List<StudentLessonFindResponse> createStudentLessonFindResponse(List<Lesson> lessons, List<LessonProgress> lessonProgresses) {
        Map<Long, LessonProgress> progressByLessonId = lessonProgresses.stream()
                .collect(Collectors.toMap(LessonProgress::getLessonId, progress -> progress));

        List<StudentLessonFindResponse> findLessons = new ArrayList<>(lessons.size());
        for (var lesson : lessons) {
            long lessonId = lesson.getId();
            LessonProgress progress = progressByLessonId.get(lessonId);
            LocalDateTime completedAt = progress == null ? null : progress.getCompletedAt();

            StudentLessonFindResponse response = lessonMapper.toStudentLessonFindResponse(lesson, completedAt);
            findLessons.add(response);
        }
        return findLessons;
    }
}
