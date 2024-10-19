package io.ylab.habittracker.model.habit;

public enum Frequency {
    EVERY_WEEK, EVERY_DAY;

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
