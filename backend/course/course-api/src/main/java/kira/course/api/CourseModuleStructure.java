package kira.course.api;

import java.util.Set;

public record CourseModuleStructure(long courseId, Set<Long> lessonsIds, Set<Long> testsIds) {}
