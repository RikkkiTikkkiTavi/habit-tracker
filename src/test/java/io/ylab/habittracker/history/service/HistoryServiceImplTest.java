package io.ylab.habittracker.history.service;

import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceImplTest {

    HistoryService hs;

    @BeforeEach
    void setUp() {
        hs = new HistoryServiceImpl();
        hs.noteHabit(1, 1);
    }

    @Test
    void firstNoteHabitCreateNewUserHistoryForUser() {
        assertNotNull(hs.getUserHistory(1));
        assertNull(hs.getUserHistory(2));
    }

    @Test
    void secondNoteHabitIncreasedCompletedHabitListForUser() {
        int oldSize = hs.getUserHistory(1).getCompletedHabits().size();
        assertEquals(1, oldSize);
        hs.noteHabit(1, 2);
        int newSize = hs.getUserHistory(1).getCompletedHabits().size();
        assertEquals(2, newSize);
    }

    @Test
    void noteHabitTwiceInRowInOneIncreaseStreakDoNotChange() {
        int streak = hs.getUserHistory(1).getCompletedHabits().get(1L).getCurrentStreak();
        assertEquals(1, streak);
        hs.noteHabit(1, 1);
        int newStreak = hs.getUserHistory(1).getCompletedHabits().get(1L).getCurrentStreak();
        assertEquals(1, newStreak);
    }

    @Test
    void generateNumberOfCompleteHabitReturnNumberOfCompleteHabit() {
        assertEquals(1, hs.generateNumberOfCompleteHabit(1, 1, LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @Test
    void generateNumberOfCompleteHabitWithIncorrectPeriodThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> hs.generateNumberOfCompleteHabit(1, 1, LocalDate.of(1900, 1, 1), LocalDate.now().plusDays(1)));
        assertEquals("Указан неверный период", e.getMessage());
    }

    @Test
    void successRateHabitWithIncorrectPeriodThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> hs.successRateHabit(1, 1, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 1, 1)));
        assertEquals("Указан неверный период", e.getMessage());
    }


    @Test
    void successRateReturnPercentOfSuccessfulCompletedHabit() {
        assertEquals(100, hs.successRateHabit(1, 1, LocalDate.now(), LocalDate.now()));
    }

    @Test
    void getMaxStreakReturnMaxStreak() {
        int streak = hs.getMaxStreak(1, 1);
        assertEquals(1, streak);
    }

    @Test
    void getCurrentStreak() {
        int streak = hs.getCurrentStreak(1, 1);
        assertEquals(1, streak);
    }

    @Test
    void getCurrentStreakThrowExceptionIfNoOneNoteHabit() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> hs.getCurrentStreak(2, 2));
        assertEquals("Вы не выполнили ни одной привычки", e.getMessage());
    }
}
