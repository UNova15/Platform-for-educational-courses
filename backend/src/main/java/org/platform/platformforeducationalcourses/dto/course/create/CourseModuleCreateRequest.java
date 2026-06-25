package org.platform.platformforeducationalcourses.dto.course.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.platform.platformforeducationalcourses.dto.lesson.CourseLessonCreateRequest;

public record CourseModuleCreateRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        @PositiveOrZero int orderIndex,
        @Size(max = 100) List<CourseLessonCreateRequest> lessons,
        @Size(max = 100) List<CourseTestCreateRequest> tests) {}
