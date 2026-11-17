package kira.user.domain.user.valueobject;

import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {
    private final String value;

    public static Email of(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new ValueObjectValidationException("Incorrect data to create email");
        }
        return new Email(email);
    }

    public static Email restore(String value){
        return new Email(value);
    }
}
