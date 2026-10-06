package kira.progress.application.port.out.external;

import common.domain.Id;
import java.util.Set;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;

public record ModuleStructure(Id<Course> courseId, Set<Id<Lesson>> lessonsIds, Set<Id<Test>> testsIds) {}
