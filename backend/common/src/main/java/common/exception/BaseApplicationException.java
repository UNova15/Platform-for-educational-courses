package common.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public abstract class BaseApplicationException extends RuntimeException {

    @Getter(AccessLevel.NONE)
    private Map<String, Object> context;

    public Map<String, Object> context() {
        return Collections.unmodifiableMap(context);
    }

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException withContext(String property, Object value) {
        if (context == null) {
            this.context = new HashMap<>();
        }

        context.put(property, value);
        return this;
    }
}
