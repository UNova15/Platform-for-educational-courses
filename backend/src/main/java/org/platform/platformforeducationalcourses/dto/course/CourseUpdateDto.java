package org.platform.platformforeducationalcourses.dto.course;

import refactor.course.domain.internal.course.Tag;

public record CourseUpdateDto(String title, String description, Tag tag, long userId, long courseId) {}
