package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.model.user.Role;
import io.ylab.habittracker.repository.DbRepositoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DbUserRoleRepositoryTest extends DbRepositoryTest {

    @Test
    void methodGetUserRoleReturnROLE_USER() {
        assertEquals(Role.USER, roleRepository.getUserRole(1));
    }
}