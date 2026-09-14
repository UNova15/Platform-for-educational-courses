package common.exception;

import lombok.Getter;
import refactor.common.exception.codes.ExceptionCode;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private final ExceptionCode code;
    private final Map<String, Object> args;

    public BaseApplicationException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.code = exceptionCode;
        this.args = new HashMap<>();
    }

    protected void setProperty(String name, Object value) {
        args.put(name, value);
    }
}
