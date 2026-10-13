package common.domain.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseApplicationException extends RuntimeException {
    private Map<String, Object> context;

    public Map<String, Object> context() {
        return context == null ? Collections.emptyMap() : Collections.unmodifiableMap(context);
    }

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException withContext(String property, Object value) {
        if(context == null){
            context = new HashMap<>();
        }

        context.put(property, value);
        return this;
    }
}
