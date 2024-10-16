package io.ylab.habittracker.habit.repo;

import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.validate.ValidationException;

import java.util.HashMap;
import java.util.Map;


public class InMemoryHabitRepository implements HabitRepository {
    private final Map<Long, Map<Long, Habit>> habits = new HashMap<>();


    @Override
    public Habit createHabit(Habit habit) {
        Long userId = habit.getUserId();
        if (habits.containsKey(userId)) {
            Map<Long, Habit> map = habits.get(userId);
            Long habitId = 1L;
            while(map.containsKey(habitId)) {
                habitId++;
            }
            habit.setId(habitId);
            map.put(habitId, habit);
            habits.put(userId, map);

        } else {
            Map<Long, Habit> newMap = new HashMap<>();
            habit.setId(1L);
            newMap.put(1L, habit);
            habits.put(userId, newMap);
        }
        return habit;
    }

    @Override
    public Habit updateHabit(Habit habit) {
        Long userId = habit.getUserId();
        if (habits.containsKey(userId)) {
            Map<Long, Habit> map = habits.get(userId);
            if (map.containsKey(habit.getId())) {
                map.put(habit.getId(), habit);
            } else {
                throw new ValidationException("Привычки с таким id нет");
            }
        } else {
            return null;
        }
        return habit;
    }

    @Override
    public Habit getHabit(long userId, long habitId) {
        return habits.get(userId).getOrDefault(habitId, null);
    }

    @Override
    public void deleteHabit(long userId, long habitId) {
        habits.get(userId).remove(habitId);
    }

    @Override
    public Map<Long, Habit> getUserHabits(long userId) {
        return habits.getOrDefault(userId, null);
    }
}
