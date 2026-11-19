package kira.user.application.exceptions;

import common.application.exception.RuleConflictException;
import kira.user.domain.AuthAggregateType;

public class ConfirmCodeException extends RuleConflictException {
    public ConfirmCodeException() {
        super("Invalid code to confirmation", AuthAggregateType.CONFIRMATION_SESSION);
    }

    public ConfirmCodeException(String message) {
        super(message, AuthAggregateType.CONFIRMATION_SESSION);
    }
}
