package org.platform.platformforeducationalcourses.service.domain;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.LessonCreateDto;
import org.platform.platformforeducationalcourses.dto.lesson.LessonCreateResponse;
import org.platform.platformforeducationalcourses.dto.lesson.LessonUpdateDto;
import org.platform.platformforeducationalcourses.exception.LessonNotFoundException;
import org.platform.platformforeducationalcourses.mapper.LessonMapper;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис для выполнения CRUD операций с сущностью уроком
 */

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public void deleteLesson(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow();

        lessonRepository.delete(lesson);
    }

    public void updateLesson(LessonUpdateDto updateDto) {
        Lesson lesson = lessonRepository.findById(updateDto.lessonId())
                .orElseThrow();
        lesson.update(updateDto.title(), updateDto.type(), updateDto.content(), updateDto.orderIndex(),
                updateDto.mandatory());

        lessonRepository.save(lesson);
    }

    public LessonCreateResponse createLesson(LessonCreateDto createDto) {
        Lesson lesson = Lesson.createNew(createDto.moduleId(), createDto.title(), createDto.type(),
                createDto.content(), createDto.orderIndex(), createDto.mandatory());

        long id = lessonRepository.save(lesson).getId();
        return lessonMapper.toLessonCreateResponse(id, createDto);
    }

    public LessonFindResponse findLesson(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        return lessonMapper.toLessonFindResponse(lesson);
    }
}
