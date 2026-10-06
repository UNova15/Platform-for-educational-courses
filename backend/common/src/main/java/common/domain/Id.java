package common.domain;

import common.exception.DomainModificationException;
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

    public static <T> Id<T> of(Long id) {
        if (id == null || id < 0) {
            throw new DomainModificationException("Id cannot be empty or negative");
        }

        return new Id<>(id);
    }
}
