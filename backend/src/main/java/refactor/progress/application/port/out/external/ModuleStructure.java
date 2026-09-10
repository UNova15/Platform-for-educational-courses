package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;

import java.util.Set;

public record ModuleStructure(Id<Course> courseId, Set<Id<Lesson>> lessonsIds, Set<Id<Test>> testsIds) {}
