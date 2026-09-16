package kira.auth.domain.token.valueobject;

import common.exception.DomainValidationException;
import common.exception.codes.CommonExceptionCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {
    private final String value;

    public static AccessToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new DomainValidationException(
                    CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, "Access token cannot be empty");
        }
        return new AccessToken(token);
    }
}
