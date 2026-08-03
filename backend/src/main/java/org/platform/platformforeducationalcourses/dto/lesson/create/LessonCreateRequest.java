package org.platform.platformforeducationalcourses.dto.lesson.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.MAX_LESSON_TITLE_SIZE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonCreateRequest(
        @NotBlank @Size(max = MAX_LESSON_TITLE_SIZE) String title,
        @NotNull ContentType type,
        @NotBlank String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {}
