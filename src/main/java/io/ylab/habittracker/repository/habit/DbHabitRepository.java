package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс для работы с базой данных
 * @see HabitRepository - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class DbHabitRepository implements HabitRepository {

    /**
     * Поле идентификатор привычки
     */
    private long id;

    /**
     * Поле объект класса провайдера
     */
    private final DBConnectionProvider dbConnectionProvider;

    /**
     * Конструктор - создание нового объекта.
     * В качестве параметра принимает объект класса DBConnectionProvider
     * В теле конструктора происходит инициализация id
     */
    public DbHabitRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
        this.id = getLastIdFromDb();
    }

    /**
     * Функция получения значения
     * @return возвращает id последней добавленной привычки
     */
    private long getLastIdFromDb() {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT MAX(id) FROM entity.habits;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("max");
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Функция создания привычки в БД
     * Метод принимает в качестве параметра привычку и сохраняет ее в БД
     * @return ссылку на объект привычку
     */
    @Override
    public Habit createHabit(Habit habit) {
        habit.setId(++id);
        String insertQuery =
                "INSERT INTO entity.habits (name,description,frequency,create_time,user_id,id) VALUES (?,?,?,?,?,?);";
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            insertHabit(habit, insertQuery, connection);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        return habit;
    }

    /**
     * Функция обновления привычки в БД
     * Метод принимает в качестве параметра привычку и обновляет ее в БД
     * @return ссылку на объект привычку
     */
    @Override
    public Habit updateHabit(Habit habit) {
        String updateQuery =
                "UPDATE entity.habits SET name=?, description=?, frequency=?, create_time=?, user_id=? WHERE id = ?;";
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            insertHabit(habit, updateQuery, connection);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        return habit;
    }

    /**
     * Функция получения привычки из БД
     * Метод принимает в качестве параметра id привычки
     * В случае отсутствия привычки в БД, возвращает null
     * @return ссылку на объект привычку
     */
    @Override
    public Habit getHabit(long habitId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitQuery = "SELECT * FROM entity.habits WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitQuery);
            preparedStatement.setLong(1, habitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToHabit(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Функция удаления привычки из БД
     * Метод принимает в качестве параметра id привычки
     * Метод ничего не возвращает
     */
    @Override
    public void deleteHabit(long userId, long habitId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String deleteHabitQuery = "DELETE FROM entity.habits cascade WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteHabitQuery);
            preparedStatement.setLong(1, habitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Функция получения привычек пользователя
     * Метод принимает в качестве параметра id пользователя
     * @return список привычек пользователя, в случае отсутствия привычек возвращает пустой лист
     */
    @Override
    public List<Habit> getUserHabits(long userId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT * FROM entity.habits WHERE user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToHabits(resultSet);
        } catch (SQLException e) {
            return List.of();
        }
    }

    /**
     * Метод создающий PreparedStatement для обновления или создания привычки
     * @see DbHabitRepository#createHabit(Habit)
     * @see DbHabitRepository#updateHabit(Habit)
     * В качестве параметров принимает привычку, запрос на сохранение или обновление, объект класса Connection
     */
    private static void insertHabit(Habit habit, String query, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, habit.getName());
            preparedStatement.setString(2, habit.getDescription());
            preparedStatement.setString(3, habit.getFrequency().toString());
            Timestamp timestamp = Timestamp.valueOf(habit.getCreateTime().atStartOfDay());
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setLong(5, habit.getUserId());
            preparedStatement.setLong(6, habit.getId());
            preparedStatement.executeUpdate();

    }

    /**
     * Метод маппер преобразует ResultSet в список привычек
     */
    private List<Habit> rowToHabits(ResultSet resultSet) throws SQLException {
        List<Habit> habits = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Frequency frequency = Frequency.valueOf(resultSet.getString("frequency"));
            LocalDate date = resultSet.getDate("create_time").toLocalDate();
            long user_id = resultSet.getLong("user_id");
            Habit habit = new Habit(name, description, frequency, user_id);
            habit.setCreateTime(date);
            habit.setId(id);
            habits.add(habit);
        }
        return habits;
    }

    /**
     * Метод маппер преобразует ResultSet в привычку
     */
    private Habit rowToHabit(final ResultSet resultSet) throws SQLException {
        resultSet.next();
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Frequency frequency = Frequency.valueOf(resultSet.getString("frequency"));
        LocalDate date = resultSet.getDate("create_time").toLocalDate();
        long user_id = resultSet.getLong("user_id");
        Habit habit = new Habit(name, description, frequency, user_id);
        habit.setCreateTime(date);
        habit.setId(resultSet.getLong("id"));
        return habit;
    }
}
