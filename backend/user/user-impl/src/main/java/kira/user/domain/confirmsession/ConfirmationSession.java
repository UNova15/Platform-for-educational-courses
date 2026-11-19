package kira.user.domain.confirmsession;

import common.domain.exception.DomainValidationException;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.confirmsession.valueobject.ConfirmationCode;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmationSession {
    private static final int INITIAL_ATTEMPT_COUNT = 5;

    private final Username username;
    private final Email email;
    private final HashedPassword hashedPassword;

    private int numberOfAttempts;
    private ConfirmationCode code;
    private Instant lastLetterSent;

    public enum VerificationResult {
        VERIFIED,
        INVALID_CODE,
        ATTEMPTS_EXHAUSTED
    }

    public static ConfirmationSession create(
            Username username, Email email, ConfirmationCode code, HashedPassword hashedPassword) {
        if (username == null || email == null || hashedPassword == null || code == null) {
            throw new DomainValidationException(
                    "Cannot create code attempt with empty data", AuthAggregateType.CONFIRMATION_SESSION);
        }
        return new ConfirmationSession(username, email, hashedPassword, INITIAL_ATTEMPT_COUNT, code, Instant.now());
    }

    public VerificationResult verifyCode(ConfirmationCode inputCode) {
        if (numberOfAttempts <= 0) {
            return VerificationResult.ATTEMPTS_EXHAUSTED;
        }
        boolean isEquals = code.equals(inputCode);

        if (!isEquals) {
            numberOfAttempts--;
            return VerificationResult.INVALID_CODE;
        }
        return VerificationResult.VERIFIED;
    }

    public void updateCodeForResend(ConfirmationCode code) {
        this.code = code;
        this.numberOfAttempts = INITIAL_ATTEMPT_COUNT;
        this.lastLetterSent = Instant.now();
    }
}
