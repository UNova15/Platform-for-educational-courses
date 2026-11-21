package kira.user.domain.code.valueobject;

import java.time.Duration;

public enum DelaySteps {
    FIRST(Duration.ofMinutes(1)),
    SECOND(Duration.ofMinutes(5)),
    THIRD(Duration.ofMinutes(10)),
    FOURTH(Duration.ofMinutes(15)),
    FIFTH(Duration.ofHours(1));

    private final Duration time;

    DelaySteps(Duration time) {
        this.time = time;
    }

    public Duration time() {
        return time;
    }

    public DelaySteps next() {
        int nextIndex = Math.min(this.ordinal() + 1, values().length - 1);
        return values()[nextIndex];
    }
}
