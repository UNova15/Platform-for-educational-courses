package org.platform.platformforeducationalcourses.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LessonEditingException extends RuntimeException{
    private final long moduleId;
    private final long lessonId;
}
