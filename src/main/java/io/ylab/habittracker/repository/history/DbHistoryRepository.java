package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class DbHistoryRepository implements HistoryRepository {

    private final DBConnectionProvider dbConnectionProvider;

    public DbHistoryRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

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
