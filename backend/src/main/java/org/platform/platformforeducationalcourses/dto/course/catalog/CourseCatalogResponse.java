package org.platform.platformforeducationalcourses.dto.course.catalog;

import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record CourseCatalogResponse(
        long id,
        long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<ModuleCatalogResponse> modules
) {
}
