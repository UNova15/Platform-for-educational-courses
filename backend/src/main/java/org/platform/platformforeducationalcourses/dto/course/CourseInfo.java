package org.platform.platformforeducationalcourses.dto.course;

import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.time.LocalDateTime;

public record CourseInfo(String title, String description, Tag tag, LocalDateTime createdAt) {
}
