package org.platform.platformforeducationalcourses.service.domain;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.ports.persistance.LessonRepository;
import org.platform.platformforeducationalcourses.dto.lesson.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.LessonUpdateRequest;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateRequest;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateResponse;
import org.platform.platformforeducationalcourses.exception.LessonNotFoundException;
import org.platform.platformforeducationalcourses.web.WebLessonMapper;
import org.springframework.stereotype.Service;

/**
 * Сервис для выполнения CRUD операций с сущностью уроком
 */
@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final WebLessonMapper lessonMapper;

    public void deleteLesson(long lessonId) {
        lessonRepository.delete(lessonId);
    }

    public void updateLesson(LessonUpdateRequest updateRequest, long moduleId, long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.update(
                updateRequest.title(),
                updateRequest.type(),
                updateRequest.content(),
                updateRequest.orderIndex(),
                updateRequest.mandatory());

        lessonRepository.save(lesson);
    }

    public LessonCreateResponse createLesson(LessonCreateRequest createRequest, long moduleId) {
        Lesson lesson = lessonMapper.fromLessonCreateRequest(createRequest, moduleId);

        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toLessonCreateResponse(savedLesson);
    }

    public LessonFindResponse findLesson(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException(lessonId));
        return lessonMapper.toLessonFindResponse(lesson);
    }
}
