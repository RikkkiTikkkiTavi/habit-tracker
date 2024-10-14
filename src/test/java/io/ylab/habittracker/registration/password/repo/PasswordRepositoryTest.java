package io.ylab.habittracker.registration.password.repo;

import io.ylab.habittracker.registration.password.Registration;
import io.ylab.habittracker.registration.role.Role;
import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    private Registration reg;

    @BeforeEach
    void setUp() {
        reg = new Registration();

        reg.registration(1, "123456");
    }

    @Test
    void authenticationReturnTrueIfPasswordRight() {
        assertTrue(reg.authentication(1, "123456"));
        assertFalse(reg.authentication(1, "123457"));
    }

    @Test
    void authorizationReturnRole() {
        assertEquals(Role.USER, reg.authorization(1));
    }

    @Test
    void registrationWithInvalidPassportThrowException() {
        ValidationException e1 = assertThrows(
                ValidationException.class,
                () -> reg.registration(1, "12345"));
        assertEquals("Пароль должен содержать не менее 6 символов", e1.getMessage());

        ValidationException e2 = assertThrows(
                ValidationException.class,
                () -> reg.registration(1, "1223 123"));
        assertEquals("Пароль не должен содержать пустых символов", e2.getMessage());

        ValidationException e3 = assertThrows(
                ValidationException.class,
                () -> reg.registration(1, "12345678901234"));
        assertEquals("Пароль должен содержать не более 12 символов", e3.getMessage());
    }
}