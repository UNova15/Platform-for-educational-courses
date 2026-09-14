package common.exception.codes;

public enum CommonExceptionCode implements ExceptionCode {
    INTERNAL_SERVER_ERROR,
    DOMAIN_VALIDATION_EXCEPTION,
    DOMAIN_MODIFICATION_EXCEPTION,
    ACCESS_EXCEPTION,
    MOT_FOUND_EXCEPTION,
    ALREADY_EXIST_EXCEPTION;

    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
