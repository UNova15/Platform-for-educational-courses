package kira.progress.application.exception;

import common.exception.named.ResourceName;

public enum ProgressResourceName implements ResourceName {
    COURSE("course"),
    LESSON("lesson"),
    TEST("test"),
    MODULE("module"),
    ATTEMPT("testAttempt"),
    ENROLLMENT("enrollment");

    ProgressResourceName(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String value() {
        return value;
    }
}
