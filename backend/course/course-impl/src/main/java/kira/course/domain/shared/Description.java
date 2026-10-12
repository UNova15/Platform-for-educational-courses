package kira.course.domain.shared;

import common.exception.core.LengthViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

// TODO вынести в adr
// Используется общий Value object(Vo) для description т.к. инвариант для всех доменных классов использующих данный Vo
// одинаковый (совпадают требования к данному полю). Как только изменится требование для данного поля в каком либо
// Domain классе произвести рефакторинг и выделить новый Vo с уникальными требованиями. На данный момент чтобы избежать
// boilerplate кода используется единый Vo.
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Description {
    public static final int MAX_LENGTH = 500;
    public static final int MIN_LENGTH = 0;

    private final String value;

    public static Description of(String description) {
        if (description == null || description.isBlank()) {
            return new Description("");
        }

        if (description.length() > MAX_LENGTH) {
            throw new LengthViolationException(
                    "Incorrect description length", description.length(), MAX_LENGTH, MIN_LENGTH);
        }

        return new Description(description);
    }
}
