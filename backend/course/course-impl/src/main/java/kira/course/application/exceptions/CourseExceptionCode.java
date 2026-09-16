package kira.course.application.exceptions;

import common.exception.codes.ExceptionCode;

public enum CourseExceptionCode implements ExceptionCode {
    COURSE_NOT_FOUND_EXCEPTION,
    MODULE_NOT_FOUND_EXCEPTION,
    COURSE_ACCESS_EXCEPTION,
    LESSON_NOT_FOUND_EXCEPTION,
    LESSON_ACCESS_EXCEPTION,
    TEST_NOT_FOUND_EXCEPTION,
    TEST_NOT_STARTED_EXCEPTION,
    MODULE_ACCESS_EXCEPTION,
    TEST_ACCESS_EXCEPTION;

    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
