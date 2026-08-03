package org.platform.platformforeducationalcourses.dto.course.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.MAX_OPTION_COUNT;
import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.MAX_QUESTION_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CourseTestQuestion(
        @NotBlank @Size(max = MAX_QUESTION_LENGTH) String question,
        @PositiveOrZero int orderIndex,
        @NotNull @Size(max = MAX_OPTION_COUNT) List<CourseOption> options) {}
