package org.platform.platformforeducationalcourses.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ModuleEditingException extends RuntimeException {
    private final long courseId;
    private final long moduleId;
}
