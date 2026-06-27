package org.platform.platformforeducationalcourses.persistance.entity.reference;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "test")
public record TestRef(@Id Long id) {}
