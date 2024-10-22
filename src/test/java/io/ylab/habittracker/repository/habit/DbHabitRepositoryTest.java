package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.repository.DbRepositoryTest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DbHabitRepositoryTest extends DbRepositoryTest {

    @Test
    void createHabit() {
        assertEquals(1, habitRepository.getHabit(1).getId());
        assertEquals(2, habitRepository.getUserHabits(1).size());
        assertEquals(habitTwo, habitRepository.createHabit(habitTwo));
    }

    @Test
    void updateHabit() {
        habitRepository.createHabit(habitOne);
        habitTwo.setId(1L);
        habitRepository.updateHabit(habitTwo);
        assertEquals(habitTwo.getName(), habitRepository.getHabit(1L).getName());
    }

    @Test
    void getHabit() {
        habitOne = habitRepository.createHabit(habitOne);
        assertEquals(habitOne.getName(), habitRepository.getHabit(1).getName());
    }

    @Test
    void deleteHabit() {
        habitRepository.createHabit(habitOne);
        habitRepository.deleteHabit(1, 1);
        assertNull(habitRepository.getHabit(1));
    }

    @Test
    void getUserHabits() {
        assertEquals(2, habitRepository.getUserHabits(1).size());
    }
}