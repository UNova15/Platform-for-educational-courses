package org.platform.platformforeducationalcourses.dto.module;

public record ModuleCreateDto(long courseId, String title, String description, int orderIndex) {}
