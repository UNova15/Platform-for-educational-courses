package org.platform.platformforeducationalcourses.dto.course.createdto;

import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.time.Instant;
import java.util.List;

public record CourseCreateDto(String title, String description, Tag tag, List<CourseModuleCreateDto> modules,
                              Instant createdAt, long teacherId) {
}
