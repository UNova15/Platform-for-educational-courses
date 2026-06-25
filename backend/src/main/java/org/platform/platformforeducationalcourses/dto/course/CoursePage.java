package org.platform.platformforeducationalcourses.dto.course;

import java.time.LocalDateTime;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CoursePage(long id, long teacherId, String title, String description, Tag tag, LocalDateTime createdAt) {}
