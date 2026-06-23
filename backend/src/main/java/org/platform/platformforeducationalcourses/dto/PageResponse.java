package org.platform.platformforeducationalcourses.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        long page,
        long size,
        long totalElements,
        long totalPages
) {
}
