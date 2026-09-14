package common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import common.wrapper.CursorResponse;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorFactory {
    public static <T, V> CursorResponse<T, V> createFrom(List<T> response, Function<T, V> cursorExtractor, int limit) {
        if (response == null || response.isEmpty()) {
            return new CursorResponse<>(List.of(), null, false);
        }

        boolean hasNext = response.size() > limit;
        List<T> content = hasNext ? response.subList(0, limit) : response;

        V cursor = cursorExtractor.apply(content.getLast());

        return new CursorResponse<>(content, cursor, hasNext);
    }
}
