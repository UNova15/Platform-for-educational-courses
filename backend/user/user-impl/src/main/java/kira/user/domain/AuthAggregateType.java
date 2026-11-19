package kira.user.domain;

import common.domain.entity.AggregateType;

public enum AuthAggregateType implements AggregateType {
    USER("user"),
    ACCESS_TOKEN("accessToken"),
    CONFIRMATION_SESSION("confirmationSession");

    private final String value;

    AuthAggregateType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
