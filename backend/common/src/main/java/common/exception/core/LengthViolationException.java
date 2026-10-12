package common.exception.core;

public class LengthViolationException extends RuleConflictException {

    public LengthViolationException(String message, int length, int max, int min) {
        super(message);
        withContext("length", length);
        withContext("maxLength", max);
        withContext("minLength", min);
    }
}
