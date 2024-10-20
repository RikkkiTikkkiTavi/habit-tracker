package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbHabitRepository implements HabitRepository {

    private long id;

    private final DBConnectionProvider dbConnectionProvider;

    public DbHabitRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
        this.id = getLastIdFromDb();
    }

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

    @Override
    public List<Habit> getUserHabits(long userId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getHabitsQuery = "SELECT * FROM entity.habits WHERE user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getHabitsQuery);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToHabits(resultSet);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

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
