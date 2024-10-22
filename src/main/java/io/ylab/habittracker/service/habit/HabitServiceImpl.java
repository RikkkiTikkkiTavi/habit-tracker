package io.ylab.habittracker.service.habit;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.repository.habit.HabitRepoManager;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.validate.Validate;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс реализации сервиса для манипуляций с привычками
 * @see HabitService - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class HabitServiceImpl implements HabitService {

    /**
     * Поле репозиторий привычек. Используется реализация объект репозитория для работы с базами данных
     */
    HabitRepository hr = HabitRepoManager.getInstance();

    /**
     * Метод добавления привычки в репозиторий.
     * Здесь происходит инициализация времени создания привычки, а так же проходит вся необходимая валидация.
     * @return метод возвращает ссылку на объект Habit
     */
    @Override
    public Habit addHabit(Habit habit, long userId) {
        Validate.checkHabit(habit, userId);
        habit.setCreateTime(LocalDate.now());
        return hr.createHabit(habit);
    }

    /**
     * Метод обновления привычки.
     * Присутствует валидация
     * @return метод возвращает ссылку на объект Habit
     */
    @Override
    public Habit editHabit(Habit habit, long userId) {
        Validate.checkHabit(habit, userId);
        return hr.updateHabit(habit);
    }

    /**
     * Метод удаления привычки.
     */
    @Override
    public void deleteHabit(long userId, long habitId) {
        hr.deleteHabit(userId, habitId);
    }

    /**
     * Метод получения привычки.
     * @return метод возвращает ссылку на объект Habit
     */
    @Override
    public Habit getHabit(long userId, long habitId) {
        Habit habit = hr.getHabit( habitId);
        Validate.checkHabit(habit, userId);
            return habit;
    }

    /**
     * Метод получения привычек конкретного пользователя
     * @return метод возвращает список привычек
     */
    @Override
    public List<Habit> getHabits(long userId) {
        List<Habit> userHabits = hr.getUserHabits(userId);
        if (userHabits == null) {
            return List.of();
        }
        return userHabits;
    }
}
