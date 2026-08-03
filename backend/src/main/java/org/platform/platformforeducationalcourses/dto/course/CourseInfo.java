package org.platform.platformforeducationalcourses.dto.course;

import java.time.LocalDateTime;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseInfo(long id, String title, String description, Tag tag, LocalDateTime createdAt) {}
