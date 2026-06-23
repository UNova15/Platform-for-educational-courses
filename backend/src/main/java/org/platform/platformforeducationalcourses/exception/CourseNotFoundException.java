package org.platform.platformforeducationalcourses.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CourseNotFoundException extends RuntimeException {
    private final long courseId;
    private final long userId;
}
