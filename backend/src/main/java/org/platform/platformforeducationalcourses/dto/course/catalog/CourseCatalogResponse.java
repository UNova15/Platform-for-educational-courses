package org.platform.platformforeducationalcourses.dto.course.catalog;

import java.time.LocalDateTime;
import java.util.List;
import refactor.course.domain.internal.course.Tag;

public record CourseCatalogResponse(
        long id,
        long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<ModuleCatalogResponse> modules) {}
