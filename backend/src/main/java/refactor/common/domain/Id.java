package refactor.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainModificationException;

//Id класс с фантомными типами для обеспечения типобезопасности разных Id
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Id<T> {
    private final long value;

    public static <T> Id<T> of(long id) {
        if (id < 0) {
            throw new DomainModificationException("Id cannot be negative");
        }

        return new Id<>(id);
    }
}
