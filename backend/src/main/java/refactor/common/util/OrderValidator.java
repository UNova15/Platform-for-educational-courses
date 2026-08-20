package refactor.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.ToIntFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderValidator {

    // Проверка последовательности на правильность ее порядковых индексов. Последовательность считается правильно
    // упорядоченной если все ее порядковые индексы идут от 1 до n (n - длина последовательности) в порядке возрастания
    // без дубликатов
    public static <T> boolean isValidSequence(List<T> items, ToIntFunction<T> orderExtractor) {
        if (items == null || items.isEmpty()) {
            return true;
        }

        int expectedSize = items.size();

        long uniqueCount = items.stream().mapToInt(orderExtractor).distinct().count();

        if (uniqueCount != expectedSize) {
            return false;
        }

        IntSummaryStatistics statistics =
                items.stream().mapToInt(orderExtractor).summaryStatistics();

        return statistics.getMax() == expectedSize && statistics.getMin() == 1;
    }
}
