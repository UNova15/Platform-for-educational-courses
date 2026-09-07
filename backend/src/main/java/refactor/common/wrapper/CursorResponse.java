package refactor.common.wrapper;

import java.util.List;

public record CursorResponse<T, V>(List<T> content, V nextCursor, boolean hasNext) {}
