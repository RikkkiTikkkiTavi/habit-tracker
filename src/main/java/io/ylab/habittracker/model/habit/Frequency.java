package io.ylab.habittracker.model.habit;
/**
 * Enum со значениями частотности выполнения привычки.
 * @autor Константин Щеглов
 */
public enum Frequency {
    EVERY_WEEK, EVERY_DAY;

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
