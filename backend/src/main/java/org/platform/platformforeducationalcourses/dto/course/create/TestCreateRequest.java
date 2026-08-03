package org.platform.platformforeducationalcourses.dto.course.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.MAX_QUESTION_COUNT;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;

public record TestCreateRequest(
        @PositiveOrZero int orderIndex,
        String description,
        @NotNull @Size(max = MAX_QUESTION_COUNT) List<CourseTestQuestion> questions) {}
