package kira.course.domain;

import common.domain.entity.AggregateType;

public enum CourseAggregateType implements AggregateType {
    COURSE("course"),
    MODULE("module"),
    TEST("test"),
    LESSON("lesson"),
    QUESTION("question"),
    ANSWER_OPTION("option");

    CourseAggregateType(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String value() {
        return value;
    }
}
