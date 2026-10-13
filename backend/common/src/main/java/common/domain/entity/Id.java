package common.domain.entity;

import common.domain.exception.ValueObjectValidationException;
import lombok.*;
import lombok.experimental.Accessors;

// Id класс с фантомными типами для обеспечения типобезопасности разных Id
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Id<T> {
    private final long value;

    public static <T> Id<T> of(long id) {
        if (id < 0) {
            throw new ValueObjectValidationException("Id cannot be empty or negative").withContext("value", id);
        }

        return new Id<>(id);
    }
}
