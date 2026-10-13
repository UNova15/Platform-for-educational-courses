package common.domain.exception;

public class LengthViolationException extends BaseApplicationException {

    public LengthViolationException(String message, int length, int max, int min) {
        super(message);
        withContext("length", length);
        withContext("maxLength", max);
        withContext("minLength", min);
    }
}
