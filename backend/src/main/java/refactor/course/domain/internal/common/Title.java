package refactor.course.domain.internal.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;


// Используется общий Value object(Vo) для description т.к. инвариант для всех доменных классов использующих данный Vo
// одинаковый (совпадают требования к данному полю). Как только изменится требование для данного поля в каком либо
// Domain классе произвести рефакторинг и выделить новый Vo с уникальными требованиями. На данный момент чтобы избежать
// boilerplate кода используется единный Vo.
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Title {
    public static final int MAX_LENGTH = 100;

    private final String value;

    public static Title of(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_LENGTH) {
            throw new DomainValidationException("Incorrect data to create course title");
        }
        return new Title(title);
    }
}
