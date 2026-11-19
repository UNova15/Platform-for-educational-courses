package kira.user.domain.confirmsession.valueobject;

import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmationCode {
    private final String value;

    public static ConfirmationCode of(String code) {
        if (code == null || code.isBlank()) {
            throw new ValueObjectValidationException("Cannot create empty confirmation code");
        }
        return new ConfirmationCode(code);
    }
}
