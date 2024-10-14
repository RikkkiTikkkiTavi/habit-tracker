package io.ylab.habittracker.user.repo;

import io.ylab.habittracker.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    UserRepository ur;
    User user;

    @BeforeEach
    void setUp() {
        ur = new InMemoryUserRepository();
        user = new User("1", "email");
        ur.addUser(user);
    }

    @Test
    void addUserSaveUserInRepo() {
        assertEquals(user, ur.getUser("email"));
    }

    @Test
    void deleteUserRemoveUserFromRepo() {
        assertNotNull(ur.getUser("email"));
        ur.deleteUser("email");
        assertNull(ur.getUser("email"));
    }

    @Test
    void updateUserReturnNewUser() {
        User newUser = new User("email", "update");
        assertEquals(newUser, ur.updateUser(newUser));
    }

    @Test
    void getUserReturnUserIfEmailContain() {
        assertNotNull(ur.getUser("email"));
        assertNull(ur.getUser("emaill"));
    }

    @Test
    void getUsersReturnUsersList() {
        assertEquals(1, ur.getUsers().size());
    }
}