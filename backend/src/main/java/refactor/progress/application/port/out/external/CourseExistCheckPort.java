package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;

public interface CourseExistCheckPort {

    boolean isCourseExist(Id<Course> courseId);
}
