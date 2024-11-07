package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.validate.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс позволяющий сохранять привычки в оперативной памяти
 * @see HabitRepository - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class InMemoryHabitRepository implements HabitRepository {
    private final Map<Long, Map<Long, Habit>> habits = new HashMap<>();

    @Override
    public Habit getHabit(long habitId) {
        return null;
    }

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
    public void deleteHabit(long userId, long habitId) {
        habits.get(userId).remove(habitId);
    }

    @Override
    public List<Habit> getUserHabits(long userId) {
       // return habits.getOrDefault(userId, null);
        return null;
    }
}
