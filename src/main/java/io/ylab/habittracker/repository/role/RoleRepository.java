package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.model.user.Role;

public interface RoleRepository {
    void addUserRole(long userId, long roleId);
    Role getUserRole(long userId);
}
