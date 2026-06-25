package org.platform.platformforeducationalcourses.domain.course.reference;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "manual_questions")
public record ManualTestRef(@Id Long id) {}
