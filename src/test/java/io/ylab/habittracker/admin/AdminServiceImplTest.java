package io.ylab.habittracker.admin;

import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {

    AdminService as;

    @BeforeEach
    void setUp() {
        as = new AdminServiceImpl();
    }

    @Test
    void getUsersReturnListOfUsersWithOnlyAdmin() {
        assertEquals(0, as.getUsers().size());
    }

    @Test
    void getUserHabitsFromNonExistUserThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> as.getUserHabits(80));
        assertEquals("У пользователя нет привычек", e.getMessage());
    }
}