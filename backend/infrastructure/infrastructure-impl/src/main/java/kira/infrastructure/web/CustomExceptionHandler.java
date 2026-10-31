package kira.infrastructure.web;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException exception) {
        loggingApplicationException(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ResourceAccessDeniedException.class)
    public ProblemDetail handleResourceAccessDenied(ResourceAccessDeniedException exception) {
        loggingApplicationException(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler({RuleConflictException.class, DomainStateException.class})
    public ProblemDetail handleConflictExceptions(TypedBaseApplicationException exception) {
        loggingApplicationException(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({
        DomainValidationException.class,
        ValueObjectValidationException.class,
        LengthViolationException.class
    })
    public ProblemDetail handleValidationException(BaseApplicationException exception) {
        loggingApplicationException(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BaseApplicationException.class)
    public ProblemDetail handleBaseApplicationException(BaseApplicationException exception) {
        log.atError().setCause(exception).log("Unhandled Application Exception: {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private void loggingApplicationException(BaseApplicationException exception) {
        var loggingEventBuilder = log.atWarn();

        if (exception.context() != null) {
            for (var entry : exception.context().entrySet()) {
                loggingEventBuilder = loggingEventBuilder.addKeyValue(entry.getKey(), entry.getValue());
            }
        }
        loggingEventBuilder.log(exception.getMessage());
    }
}
