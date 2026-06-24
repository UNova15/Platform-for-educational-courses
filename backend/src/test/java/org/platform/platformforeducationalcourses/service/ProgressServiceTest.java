package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.repository.ProgressRepository;
import org.platform.platformforeducationalcourses.service.domain.ProgressService;

@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private ProgressService progressService;

    @Test
    void findLessonProgressByLessonsIds_Success() {
        Lesson lesson1 = mock(Lesson.class);
        when(lesson1.getId()).thenReturn(10L);

        Lesson lesson2 = mock(Lesson.class);
        when(lesson2.getId()).thenReturn(20L);

        List<Lesson> lessons = List.of(lesson1, lesson2);
        List<Long> expectedIds = List.of(10L, 20L);

        List<LessonProgress> expectedProgress = List.of(mock(LessonProgress.class));
        when(progressRepository.findAllByUserIdAndLessonIdIn(1L, expectedIds)).thenReturn(expectedProgress);

        List<LessonProgress> actualProgress = progressService.findLessonProgressByLessonsIds(1L, lessons);

        assertEquals(expectedProgress, actualProgress);
    }
}
