package io.ylab.habittracker.registration.role.repo;

import io.ylab.habittracker.registration.role.Role;

import java.util.HashMap;
import java.util.Map;

public class UserRole {

    private final Map<Long, Role> usersRoles = new HashMap<>();

    public void addRole(long id, Role role) {
        usersRoles.put(id, role);
    }

    public Role getRole(long userId) {
        return usersRoles.get(userId);
    }
}
