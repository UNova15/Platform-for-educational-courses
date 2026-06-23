package org.platform.platformforeducationalcourses.dto.module;

public record ModuleUpdateDto(long courseId, long moduleId,String title, String description, int order_index) {
}
