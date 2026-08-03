package org.platform.platformforeducationalcourses.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import org.platform.platformforeducationalcourses.dto.lesson.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateResponse;

@Mapper
public interface LessonMapper {
    LessonFindResponse toLessonFindResponse(Lesson lesson);

    LessonCreateResponse toLessonCreateResponse(Lesson lesson);

    StudentLessonFindResponse toStudentLessonFindResponse(Lesson lesson, LocalDateTime completedAt);
}
