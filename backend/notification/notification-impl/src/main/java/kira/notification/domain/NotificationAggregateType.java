package kira.notification.domain;

import common.domain.entity.AggregateType;

public enum NotificationAggregateType implements AggregateType {
    USER("user"),
    COURSE("course"),
    TEST("test");

    private final String value;

    NotificationAggregateType(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
