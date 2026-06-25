package org.platform.platformforeducationalcourses.dto.course;

import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseUpdateDto(String title, String description, Tag tag, long userId, long courseId) {}
