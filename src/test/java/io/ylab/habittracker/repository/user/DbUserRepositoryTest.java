package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.repository.DbRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DbUserRepositoryTest extends DbRepositoryTest {

    User userTwo;

    @BeforeEach
    void setUp() {
        userTwo = new User("userTwo", "email@2");
        userTwo.setPassword("password");
    }

    @Test
    void addUserIncreaseSizeOfUserByOne() {
        assertEquals(2, userRepository.getUsers().size());
        userRepository.addUser(userTwo);
        assertEquals(3, userRepository.getUsers().size());
    }

    @Test
    void deleteUserDecreaseSizeOfUserByOne() {
        assertEquals(2, userRepository.getUsers().size());
        userRepository.deleteUser("email");
        assertEquals(1, userRepository.getUsers().size());
    }

    @Test
    void updateUserNameChangeUserName() {
        assertEquals("1", userRepository.getUser("email").getName());
        userOne.setName("newName");
        userRepository.updateUser(userOne);
        assertEquals("newName", userRepository.getUser("email").getName());
    }

    @Test
    void getUserReturnUser() {
        assertEquals(User.class, userRepository.getUser("email").getClass());
    }

    @Test
    void getUsersReturnListOfUsers() {
        assertEquals(2, userRepository.getUsers().size());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteUser("email@2");
    }
}