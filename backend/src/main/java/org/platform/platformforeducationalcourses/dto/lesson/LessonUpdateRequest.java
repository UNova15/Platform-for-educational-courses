package org.platform.platformforeducationalcourses.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonUpdateRequest(@NotBlank @Size(max = 100) String title,
                                  @PositiveOrZero int moduleId,
                                  @PositiveOrZero int orderIndex,
                                  @NotNull ContentType type,
                                  @NotBlank @Size(max = 1000) String content,
                                  boolean mandatory) {
}
