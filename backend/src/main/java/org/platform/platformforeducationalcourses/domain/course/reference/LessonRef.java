package org.platform.platformforeducationalcourses.domain.course.reference;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("lessons")
public record LessonRef(@Id Long id) {}
