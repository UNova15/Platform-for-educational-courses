package kira.user.domain.code.valueobject;

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
public class Code {
    public static final int CODE_LENGTH = 6;

    private final String value;

    public static Code of(String code) {
        if (code == null || code.isBlank() || code.length() != CODE_LENGTH) {
            throw new ValueObjectValidationException("Cannot create confirmation code with invalid data");
        }
        return new Code(code);
    }
}
