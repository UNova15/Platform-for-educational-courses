package org.platform.platformforeducationalcourses.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import refactor.course.domain.lesson.Lesson;
import org.platform.platformforeducationalcourses.dto.lesson.*;
import refactor.course.application.port.in.lesson.query.LessonQueryResult;
import refactor.course.application.port.in.lesson.create.LessonCreateResult;

@Mapper
public interface LessonMapper {
    LessonQueryResult toLessonFindResponse(Lesson lesson);

    LessonCreateResult toLessonCreateResponse(Lesson lesson);

    StudentLessonFindResponse toStudentLessonFindResponse(Lesson lesson, LocalDateTime completedAt);
}
