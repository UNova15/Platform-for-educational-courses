package org.platform.platformforeducationalcourses.persistance.entity.reference;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//TODO заменить на AggregateReference
@Table(name = "modules")
public record ModuleRef(@Id Long id) {}
