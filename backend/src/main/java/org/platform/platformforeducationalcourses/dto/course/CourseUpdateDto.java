package org.platform.platformforeducationalcourses.dto.course;

import refactor.course.implementation.domain.course.Tag;

public record CourseUpdateDto(String title, String description, Tag tag, long userId, long courseId) {}
