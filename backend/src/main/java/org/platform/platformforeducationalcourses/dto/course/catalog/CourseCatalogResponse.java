package org.platform.platformforeducationalcourses.dto.course.catalog;

import java.time.LocalDateTime;
import java.util.List;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseCatalogResponse(
        long id,
        long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<ModuleCatalogResponse> modules) {}
