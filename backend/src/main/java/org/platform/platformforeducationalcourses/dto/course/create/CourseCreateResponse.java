package org.platform.platformforeducationalcourses.dto.course.create;

import java.time.LocalDateTime;

public record CourseCreateResponse(long courseId, String title, LocalDateTime createdAt) {

}
