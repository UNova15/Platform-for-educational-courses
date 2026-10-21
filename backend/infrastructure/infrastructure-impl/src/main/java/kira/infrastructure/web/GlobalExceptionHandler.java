package kira.infrastructure.web;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleControllerAccessDenied(AccessDeniedException exception) {
        log.warn("Access denied by Spring Security: {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Доступ запрещен");
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMessageNotReadable(HttpMessageNotReadableException exception) {
        log.warn("Malformed JSON request: {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Некорректный формат запроса (JSON)");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Ошибка валидации данных");

        log.warn("Validation failed: {}", errorMessage);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(TypedBaseApplicationException.class)
    public ProblemDetail handleBaseApplicationException(TypedBaseApplicationException exception) {
        log.atError().setCause(exception).log("Unhandled Application Exception: {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка приложения");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception exception) {
        log.atError().setCause(exception).log("Internal server error: {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
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
