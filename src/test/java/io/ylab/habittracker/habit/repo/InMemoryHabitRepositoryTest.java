package io.ylab.habittracker.habit.repo;

import io.ylab.habittracker.habit.model.Frequency;
import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHabitRepositoryTest {

    private HabitRepository repo;
    private Habit habit1;
    private Habit habit5;


    @BeforeEach
    void setUp() {
        repo = new InMemoryHabitRepository();
        habit1 = new Habit("1", "1", Frequency.EVERY_DAY, 1L);
        repo.createHabit(habit1);

        habit5 = new Habit("5", "5", Frequency.EVERY_DAY, 2L);
    }

    @Test
    void createHabitReturnHabit() {
        Habit habit2 = new Habit("2", "2", Frequency.EVERY_DAY, 1L);
        habit2.setId(2L);
        repo.createHabit(habit2);
        assertEquals(habit2, repo.createHabit(habit2));
    }

    @Test
    void createHabitSaveHabitInRepo() {
        assertEquals(habit1, repo.getHabit(1, 1));
    }

    @Test
    void deleteHabitRemoveHabitFromRepo() {
        repo.deleteHabit(1, 1);
        assertNull(repo.getHabit(1, 1));
    }

    @Test
    void UpdateRepoReturnNullIfRepoNotContainsKeyOfUser() {
        assertNull(repo.updateHabit(habit5));
    }

    @Test
    void UpdateRepoThrowExceptionIfRepoNotContainsKeyHabit() {
        habit1.setId(2L);
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> repo.updateHabit(habit1));
        assertEquals("Привычки с таким id нет", e.getMessage());
    }

    @Test
    void getHabitReturnNullForNonExistHabit() {
        assertNull(repo.getHabit(1, 5));
    }

    @Test
    void getUserHabitsReturnMapOfHabits() {
        Map<Long, Habit> actual = repo.getUserHabits(1);
        Map<Long, Habit> expected = new HashMap<>();
        expected.put(1L, habit1);
        assertEquals(expected, actual);
    }

    @Test
    void ifHabitContainInMapThenUpdateReturnHabit() {
        Habit updateHabit = new Habit("update", "update", Frequency.EVERY_DAY, 1L);
        updateHabit.setId(1L);
        assertEquals(updateHabit, repo.updateHabit(updateHabit));
    }
}