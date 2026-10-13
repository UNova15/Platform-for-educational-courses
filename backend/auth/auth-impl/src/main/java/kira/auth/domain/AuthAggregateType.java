package kira.auth.domain;

import common.domain.entity.AggregateType;

public enum AuthAggregateType implements AggregateType {
    USER("user"),
    ACCESS_TOKEN("accessToken");

    private final String value;

    AuthAggregateType(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
