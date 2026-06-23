package org.platform.platformforeducationalcourses.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistException extends RuntimeException {
    private final String userLogin;
}
