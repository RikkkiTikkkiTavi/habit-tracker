package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс для манипуляций с историей привычек в БД
 * @see HabitRepository - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class DbHistoryRepository implements HistoryRepository {

    /**
     * Поле объект класса провайдера
     */
    private final DBConnectionProvider dbConnectionProvider;

    /**
     * Конструктор - создание нового объекта.
     * В качестве параметра принимает объект класса DBConnectionProvider
     */
    public DbHistoryRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    /**
     * Функция сохранения истории выполнения привычки в базе данных
     */
    @Override
    public void addUserHistory(HabitHistory hh) {
        String insertQuery =
                "INSERT INTO entity.habit_history(user_id, habit_id, date, streak) VALUES (?, ?, ?, ?)";

        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            insertHabitHistory(hh, insertQuery,connection);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Функция получения истории выполнения привычки в базе данных
     * @param userId - указывает id пользователя обладателя привычек
     */
    @Override
    public List<HabitHistory> getUserHistory(long userId, LocalDate from, LocalDate to) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT * FROM entity.habit_history WHERE user_id = ? AND date between ? AND ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            preparedStatement.setLong(1, userId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(from.atStartOfDay()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(to.atStartOfDay()));
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToHabitsHistory(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    /**
     * Функция получения конкретной истории за конкретный день
     * @param habitId - id привычки
     * @param date - день исполнения привычки
     */
    @Override
    public HabitHistory getHistory(long habitId, LocalDate date) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT * FROM entity.habit_history WHERE habit_id = ? AND date = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            preparedStatement.setLong(1, habitId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToHistory(resultSet);
        } catch (SQLException e) {
            System.out.println("История не найдена");
            return null;
        }
    }

    /**
     * Функция возвращает дату выполнения привычки в последний раз
     */
    @Override
    public LocalDate getLastNoteDate(long habitId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT MAX(date) FROM entity.habit_history WHERE habit_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            preparedStatement.setLong(1, habitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
                if(resultSet.getDate("max") != null){
                    return resultSet.getDate("max").toLocalDate();
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("История не найдена");
            return null;
        }
    }

    /**
     * Метод создающий PreparedStatement для создания истории привычки
     * @see DbHistoryRepository#addUserHistory(HabitHistory)
     * В качестве параметров принимает сохраняему историю, sql запрос на сохранение, объект класса Connection
     */
    private static void insertHabitHistory(HabitHistory habitHistory, String query, Connection connection)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, habitHistory.getUserId());
        preparedStatement.setLong(2, habitHistory.getHabitId());
        Timestamp timestamp = Timestamp.valueOf(habitHistory.getDate().atStartOfDay());
        preparedStatement.setTimestamp(3, timestamp);
        preparedStatement.setLong(4, habitHistory.getStreak());
        preparedStatement.executeUpdate();
}

    /**
     * Метод маппер преобразует ResultSet в список историю
     * @see DbHistoryRepository#addUserHistory(HabitHistory)
     */
private List<HabitHistory> rowToHabitsHistory(ResultSet resultSet) throws SQLException {
    List<HabitHistory> histories = new ArrayList<>();
    while (resultSet.next()) {
        long user_id = resultSet.getLong("user_id");
        long habit_id = resultSet.getLong("user_id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int streak = resultSet.getInt("streak");
        HabitHistory habitHistory = new HabitHistory(user_id, habit_id, date);
        habitHistory.setStreak(streak);
        histories.add(habitHistory);
    }
    return histories;
}

    /**
     * Метод маппер преобразует ResultSet в одну конкретную отметку выполнения привычки
     * @see DbHistoryRepository#getHistory(long, LocalDate)
     */
    private HabitHistory rowToHistory(final ResultSet resultSet) throws SQLException {
        resultSet.next();
        long user_id = resultSet.getLong("user_id");
        long habit_id = resultSet.getLong("user_id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int streak = resultSet.getInt("streak");
        HabitHistory hh = new HabitHistory(user_id, habit_id, date);
        hh.setStreak(streak);
        return hh;
    }
}
