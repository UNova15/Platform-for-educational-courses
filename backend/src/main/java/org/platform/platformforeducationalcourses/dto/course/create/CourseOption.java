package org.platform.platformforeducationalcourses.dto.course.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.MAX_OPTION_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseOption(
        @NotBlank @Size(max = MAX_OPTION_LENGTH) String option, boolean isCorrect) {}
