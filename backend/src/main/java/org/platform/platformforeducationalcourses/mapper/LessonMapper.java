package org.platform.platformforeducationalcourses.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.*;

@Mapper
public interface LessonMapper {
    LessonFindResponse toLessonFindResponse(Lesson lesson);

    LessonCreateDto toLessonCreateDto(long moduleId, CourseLessonCreateRequest request);

    LessonCreateResponse toLessonCreateResponse(long id, LessonCreateDto createDto);

    LessonUpdateDto toLessonUpdateDto(long lessonId, long moduleId, LessonUpdateRequest request);

    StudentLessonFindResponse toStudentLessonFindResponse(Lesson lesson, LocalDateTime completedAt);
}
