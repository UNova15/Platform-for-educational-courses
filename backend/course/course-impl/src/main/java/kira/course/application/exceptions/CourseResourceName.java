package kira.course.application.exceptions;

import common.exception.named.ResourceName;

public enum CourseResourceName implements ResourceName {
    COURSE("course"),
    MODULE("module"),
    TEST("test"),
    LESSON("lesson");

    CourseResourceName(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String value() {
        return value;
    }
}
