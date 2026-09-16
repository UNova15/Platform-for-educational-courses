package kira.progress.application.port.out.external;

import common.domain.Id;
import kira.progress.domain.markers.Course;

public interface CourseExistCheckPort {
    boolean isCourseExist(Id<Course> courseId);
}
