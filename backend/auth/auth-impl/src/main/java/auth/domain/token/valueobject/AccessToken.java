package auth.domain.token.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {
    private final String value;

    public static AccessToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new DomainValidationException("Access token cannot be empty");
        }
        return new AccessToken(token);
    }
    
}
