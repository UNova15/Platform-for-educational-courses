package kira.auth.adapter.in.web.exceptionhandler;

import common.domain.exception.BaseApplicationException;
import kira.auth.application.exceptions.AuthenticationException;
import kira.auth.application.exceptions.PersistenceConsistencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException exception) {
        loggingApplicationException(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(PersistenceConsistencyException.class)
    public ProblemDetail handlePersistenceConsistencyException(PersistenceConsistencyException exception) {
        var logger = log.atError().setCause(exception);

        if (exception.context() != null) {
            for (var entry : exception.context().entrySet()) {
                logger.addKeyValue(entry.getKey(), entry.getValue());
            }
        }
        logger.log("Нарушена целостность данных: {}", exception.getMessage());

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка авторизации");
    }

    private void loggingApplicationException(BaseApplicationException exception) {
        var loggingEventBuilder = log.atWarn();

        if (exception.context() != null) {
            for (var entry : exception.context().entrySet()) {
                loggingEventBuilder.addKeyValue(entry.getKey(), entry.getValue());
            }
        }
        loggingEventBuilder.log(exception.getMessage());
    }
}
