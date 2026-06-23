package org.platform.platformforeducationalcourses.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LessonNotFoundException extends RuntimeException {
    private final long lessonId;
}
