package org.platform.platformforeducationalcourses.web;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.lesson.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateRequest;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateResponse;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebLessonMapper {
    private final LessonMapper mapper;

    public List<Lesson> fromListCreateRequest(List<LessonCreateRequest> lessons, long moduleId) {
        return lessons.stream()
                .map(lesson -> fromLessonCreateRequest(lesson, moduleId))
                .toList();
    }

    public Lesson fromLessonCreateRequest(LessonCreateRequest lesson, long moduleId) {
        return Lesson.createNew(
                moduleId, lesson.title(), lesson.type(), lesson.content(), lesson.orderIndex(), lesson.mandatory());
    }

    public LessonCreateResponse toLessonCreateResponse(Lesson lesson) {
        return mapper.toLessonCreateResponse(lesson);
    }

    public LessonFindResponse toLessonFindResponse(Lesson lesson) {
        return mapper.toLessonFindResponse(lesson);
    }
}
