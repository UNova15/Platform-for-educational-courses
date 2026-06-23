package org.platform.platformforeducationalcourses.creator.factory;

import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.course.createdto.LessonsCourseCreateDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonFactory {
    public List<Lesson> createLessonFromCreateDto(List<LessonsCourseCreateDto> lessons, long moduleId) {
        return lessons.stream()
                .map(lesson -> Lesson.createNew(moduleId, lesson.title(), lesson.type(),
                        lesson.content(), lesson.orderIndex(), lesson.mandatory()))
                .toList();
    }
}
