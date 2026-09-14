package progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;

import java.util.Set;

public record ModuleStructure(Id<Course> courseId, Set<Id<Lesson>> lessonsIds, Set<Id<Test>> testsIds) {}
