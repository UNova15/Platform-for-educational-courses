package org.platform.platformforeducationalcourses.dto.course.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.platform.platformforeducationalcourses.dto.lesson.create.LessonCreateRequest;

public record ModuleCreateRequest(
        @NotBlank @Size(max = MAX_MODULE_TITLE_SIZE) String title,
        @Size(max = MAX_MODULE_DESCRIPTION_SIZE) String description,
        @PositiveOrZero int orderIndex,
        @Size(max = MAX_LESSONS_COUNT) List<LessonCreateRequest> lessons,
        @Size(max = MAX_TESTS_COUNT) List<TestCreateRequest> tests) {}
