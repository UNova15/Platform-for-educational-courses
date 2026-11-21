package kira.user.domain.code;

import common.domain.exception.DomainValidationException;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.code.valueobject.DelaySteps;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmationSession {
    public static final Duration TTL = Duration.ofMinutes(30);
    private static final int INITIAL_INPUT_ATTEMPT_COUNT = 5;

    private final Username username;
    private final Email email;
    private Code code;
    private final HashedPassword hashedPassword;

    private int numberOfInputAttempts;

    private DelaySteps step;
    private Instant lastUpdateTime;

    private Instant expireAt;
    // переделать на исключение и boolean?
    public enum VerificationResult {
        VERIFIED,
        INVALID_CODE,
        ATTEMPTS_EXHAUSTED
    }

    public static ConfirmationSession create(Username username, Email email, Code code, HashedPassword hashedPassword) {
        if (username == null || email == null || hashedPassword == null || code == null) {
            throw new DomainValidationException(
                    "Cannot create code attempt with empty data", AuthAggregateType.CONFIRMATION_SESSION);
        }

        var now = Instant.now();
        var expire = now.plus(TTL);
        var step = DelaySteps.FIRST;
        return new ConfirmationSession(
                username, email, code, hashedPassword, INITIAL_INPUT_ATTEMPT_COUNT, step, now, expire);
    }

    public static ConfirmationSession restore(
            Username username,
            Email email,
            Code code,
            HashedPassword hashedPassword,
            int numberOfInputAttempts,
            DelaySteps step,
            Instant lastLetterSent,
            Instant expireAt) {
        return new ConfirmationSession(
                username, email, code, hashedPassword, numberOfInputAttempts, step, lastLetterSent, expireAt);
    }

    public VerificationResult verifyCode(Code inputCode) {
        if (numberOfInputAttempts <= 0) {
            return VerificationResult.ATTEMPTS_EXHAUSTED;
        }
        if (code.equals(inputCode)) {
            return VerificationResult.VERIFIED;
        }

        numberOfInputAttempts--;
        return numberOfInputAttempts <= 0 ? VerificationResult.ATTEMPTS_EXHAUSTED : VerificationResult.INVALID_CODE;
    }

    public void updateCodeForResend(Code code) {
        var stepTime = step.time();
        var retryTime = lastUpdateTime.plus(stepTime);

        var now = Instant.now();
        if (now.isBefore(retryTime)) {
            throw new DomainValidationException(
                            "Cannot update the code because not enough time has passed. Repeat attempt %s"
                                    .formatted(retryTime),
                            AuthAggregateType.CONFIRMATION_SESSION)
                    .withContext("RetryTime", retryTime);
        }
        this.code = code;
        this.step = step.next();
        this.numberOfInputAttempts = INITIAL_INPUT_ATTEMPT_COUNT;
        this.lastUpdateTime = Instant.now();
        this.expireAt = Instant.now().plus(TTL);
    }
}
