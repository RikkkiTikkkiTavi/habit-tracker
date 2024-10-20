package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.model.user.Role;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.validate.ValidationException;

import java.sql.*;

public class DbUserRoleRepository implements RoleRepository {

    private final DBConnectionProvider dbConnectionProvider;

    public DbUserRoleRepository(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    @Override
    public void addUserRole(long userId, long roleId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String roleQuery = "INSERT INTO entity.user_roles (user_id, role_id) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(roleQuery);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public Role getUserRole(long userId) {
        try (Connection connection = this.dbConnectionProvider.getConnection()) {
            String roleQuery =
                    "SELECT name FROM entity.roles r JOIN entity.user_roles ur ON r.id=ur.role_id where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(roleQuery);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Role role = null;
            while (resultSet.next()) {
                role = Role.valueOf(resultSet.getString("name"));
            }
            return role;
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
