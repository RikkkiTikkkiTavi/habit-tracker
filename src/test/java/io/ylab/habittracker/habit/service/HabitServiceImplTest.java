package io.ylab.habittracker.habit.service;

import io.ylab.habittracker.habit.model.Frequency;
import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class HabitServiceImplTest {

    private HabitService hr;
    private Habit habit1;
    private Habit habit2;

    @BeforeEach
    void setUp() {
        hr = new HabitServiceImpl();
        habit1 = new Habit("1", "1", Frequency.EVERY_DAY, 1L);
        hr.addHabit(habit1, 1);
        habit2 = new Habit("2", "2", Frequency.EVERY_DAY, 1L);
    }

    @Test
    void addHabitReturnHabit() {
        assertEquals(habit2, hr.addHabit(habit2, 1));
    }

    @Test
    void addInvalidHabitThrowException() {
        habit1.setName("");
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> hr.addHabit(habit1, 1));
        assertEquals("Имя не может быть пустым и должно содержать не более 20 символов", e.getMessage());
    }

    @Test
    void editHabitReturnUpdatedHabit() {
        habit2.setId(1L);
        assertEquals(habit2, hr.editHabit(habit2));
    }
}