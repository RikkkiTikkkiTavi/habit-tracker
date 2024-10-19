package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.model.user.Status;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.role.RoleManager;
import io.ylab.habittracker.repository.role.RoleRepository;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserRepository implements UserRepository {

    private static long id;

    private final static RoleRepository roleRepository = RoleManager.getInstance();

    private final DBConnectionProvider dbConnectionProvider;

    public DbUserRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    @Override
    public User addUser(User user) {
        user.setId(++id);
        String insertQuery = "INSERT INTO entity.users (name, email, status, password, id) VALUES (?,?,?,?,?);";
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            insertUser(user, insertQuery, connection);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        roleRepository.addUserRole(id, 1);
        return user;
    }

    @Override
    public void deleteUser(String email) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String deleteUsersQuery = "DELETE FROM entity.users cascade WHERE email = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUsersQuery);
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user) {
        String updateQuery = "UPDATE entity.users SET name = ?, email = ?, status = ?, password = ? WHERE id = ?;";
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            insertUser(user, updateQuery, connection);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        return user;
    }

    @Override
    public User getUser(String email) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getUsersQuery = "SELECT * FROM entity.users WHERE email = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getUsersQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return rowToUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String getUsersQuery = "SELECT * FROM entity.users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getUsersQuery);
            return rowToUsers(resultSet);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static void insertUser(User user, String query, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getStatus().toString());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setLong(5, user.getId());
        preparedStatement.executeUpdate();
    }

    private List<User> rowToUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Status status = Status.valueOf(resultSet.getString("status"));
            User user = new User(name, email);
            user.setStatus(status);
            user.setId(id);
            users.add(user);
        }
        return users;
    }

    private User rowToUser(final ResultSet resultSet) throws SQLException {
        User user;
        resultSet.next();
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Status status = Status.valueOf(resultSet.getString("status"));
        String password = resultSet.getString("password");
        user = new User(name, email);
        user.setStatus(status);
        user.setId(resultSet.getLong("id"));
        user.setPassword(password);
        return user;
    }
}
