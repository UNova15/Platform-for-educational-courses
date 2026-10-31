package kira.progress.domain;

import common.domain.entity.AggregateType;

public enum ProgressAggregateType implements AggregateType {
    COURSE("course"),
    LESSON("lesson"),
    TEST("test"),
    MODULE("module"),
    ATTEMPT("testAttempt"),
    ENROLLMENT("enrollment"),
    TEST_ANSWER("testAnswer");

    ProgressAggregateType(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String value() {
        return value;
    }
}
