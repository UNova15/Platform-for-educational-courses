package org.platform.platformforeducationalcourses.dto.test;

public record TestCreateResponse(
        long id,
        long moduleId,
        String description,
        int orderIndex
) {
}
