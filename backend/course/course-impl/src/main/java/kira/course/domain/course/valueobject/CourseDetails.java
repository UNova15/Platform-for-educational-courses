package kira.course.domain.course.valueobject;

import common.domain.entity.Id;
import common.domain.exception.DomainStateException;
import common.domain.exception.ValueObjectValidationException;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDetails {
    private final Id<Course> courseId;
    private final CourseStatus status;

    public static CourseDetails of(Id<Course> courseId, CourseStatus status) {
        if (courseId == null || status == null) {
            throw new ValueObjectValidationException("Empty data to create course details");
        }
        return new CourseDetails(courseId, status);
    }

    public void verifyCanChangeContent() {
        if (status == CourseStatus.ARCHIVED) {
            throw new DomainStateException(
                    "Cannot change course content in archived state", CourseAggregateType.COURSE, courseId);
        }
    }

    public void verifyCanPublishContent() {
        if (status != CourseStatus.PUBLISHED) {
            throw new DomainStateException(
                    "Cannot publish content in not published course", CourseAggregateType.COURSE, courseId);
        }
    }
}
