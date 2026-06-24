package org.platform.platformforeducationalcourses.domain.course.reference;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules")
public record ModuleRef(@Id Long id) {}
