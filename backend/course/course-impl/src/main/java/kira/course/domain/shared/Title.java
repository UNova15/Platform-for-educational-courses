package kira.course.domain.shared;

import common.exception.core.DomainValidationException;
import common.exception.core.LengthViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
// TODO вынести в adr
// Используется общий Value object(Vo) для title т.к. инвариант для всех доменных классов использующих данный Vo
// одинаковый (совпадают требования к данному полю). Как только изменится требование для данного поля в каком либо
// Domain классе произвести рефакторинг и выделить новый Vo с уникальными требованиями. На данный момент чтобы избежать
// boilerplate кода используется единый Vo.
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Title {
    public static final int MAX_LENGTH = 100;
    public static final int MIN_LENGTH = 1;

    private final String value;

    public static Title of(String title) {
        if (title == null) {
            throw new DomainValidationException("Empty data to create course title");
        }

        if (title.length() <= MIN_LENGTH || title.length() > MAX_LENGTH) {
            throw new LengthViolationException("Incorrect title length", title.length(), MAX_LENGTH, MIN_LENGTH);
        }

        return new Title(title);
    }
}
