package kira.progress.api;

import java.util.List;

public interface EnrollmentProviderQuery {
    List<Long> findCoursesIdsThatUsersIsEnrolledIn(long userId);
}
