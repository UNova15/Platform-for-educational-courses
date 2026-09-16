package common.exception;

import common.exception.codes.ExceptionCode;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private final ExceptionCode code;

    @Getter(AccessLevel.NONE)
    private final Map<String, Object> args;

    public Map<String, Object> getArgs() {
        return Collections.unmodifiableMap(args);
    }

    public BaseApplicationException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.code = exceptionCode;
        this.args = new HashMap<>();
    }

    public BaseApplicationException(ExceptionCode exceptionCode, String message, Map<String, Object> args) {
        super(message);
        this.code = exceptionCode;
        this.args = args;
    }

    protected void setProperty(String name, Object value) {
        args.put(name, value);
    }
}
