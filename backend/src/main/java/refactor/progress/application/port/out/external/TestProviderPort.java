package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Test;

import java.util.Optional;

public interface TestProviderPort {
    Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId);
}
