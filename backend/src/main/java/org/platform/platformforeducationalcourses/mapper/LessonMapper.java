package org.platform.platformforeducationalcourses.mapper;

import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;

import java.time.LocalDateTime;

@Mapper
public interface LessonMapper {
    LessonFindResponse toLessonFindResponse(Lesson lesson);

    LessonCreateDto toLessonCreateDto(long moduleId, CourseLessonCreateRequest request);

    LessonCreateResponse toLessonCreateResponse(long id, LessonCreateDto createDto);

    LessonUpdateDto toLessonUpdateDto(long lessonId, long moduleId, LessonUpdateRequest request);

    StudentLessonFindResponse toStudentLessonFindResponse(Lesson lesson, LocalDateTime completedAt);
}
