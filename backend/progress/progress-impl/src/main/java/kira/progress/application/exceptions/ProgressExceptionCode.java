package kira.progress.application.exceptions;

import common.exception.codes.ExceptionCode;

public enum ProgressExceptionCode implements ExceptionCode {
    COURSE_ACCESS_EXCEPTION,
    LESSON_ACCESS_EXCEPTION,
    TEST_ACCESS_EXCEPTION,
    TEST_ATTEMPT_NOT_FOUND_EXCEPTION,
    LESSON_NOT_FOUND_EXCEPTION,
    MODULE_NOT_FOUND_EXCEPTION,
    TEST_NOT_FOUND_EXCEPTION,
    TEST_ALREADY_COMPLETED_EXCEPTION,
    COURSE_NOT_FOUND_EXCEPTION,
    ENROLLMENT_ALREADY_EXIST_EXCEPTION;


    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
