package kira.auth.application.exceptions;

import common.exception.named.ResourceName;

public enum AuthResourceName implements ResourceName {
    USER("user"),
    ACCESS_TOKEN("accessToken");

    private final String value;

    AuthResourceName(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
