package org.platform.platformforeducationalcourses.dto.module;

public record ModuleFindResponse(long id, long courseId, String title, String description, int orderIndex) {
}
