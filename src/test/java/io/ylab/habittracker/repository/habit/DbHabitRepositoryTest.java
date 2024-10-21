package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.LiquibaseScripts;
import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.role.DbUserRoleRepository;
import io.ylab.habittracker.repository.role.RoleRepository;
import io.ylab.habittracker.repository.user.DbUserRepository;
import io.ylab.habittracker.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DbHabitRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    HabitRepository hh;
    UserRepository ur;
    RoleRepository rr;
    Habit habitOne;
    Habit habitTwo;

    @BeforeAll
    static void beforeAll() {
        postgres.start();

    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionProvider connectionProvider = new DBConnectionProvider(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        LiquibaseScripts.setChangeSet(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());

        hh = new DbHabitRepository(connectionProvider);
        rr = new DbUserRoleRepository(connectionProvider);
        ur = new DbUserRepository(connectionProvider, rr);

        User userOne = new User("1","email");
        userOne.setPassword("password");
        ur.addUser(userOne);

        habitOne = new Habit("1", "1", Frequency.EVERY_DAY, 1L);
        habitTwo = new Habit("2", "2", Frequency.EVERY_DAY, 1L);
    }

    @AfterEach
    void tearDown() {
        ur.deleteUser("email");
    }

    @Test
    void createHabit() {
        hh.createHabit(habitOne);
        assertEquals(1, hh.getHabit(1).getId());
        assertEquals(1,hh.getUserHabits(1).size());
        assertEquals(habitTwo, hh.createHabit(habitTwo));
    }

    @Test
    void updateHabit() {
        hh.createHabit(habitOne);
        habitTwo.setId(1L);
        hh.updateHabit(habitTwo);
        assertEquals(habitTwo.getName(), hh.getHabit(1L).getName());
    }

    @Test
    void getHabit() {
        habitOne = hh.createHabit(habitOne);
        assertEquals(habitOne.getName(), hh.getHabit(1).getName());
    }

    @Test
    void deleteHabit() {
        hh.createHabit(habitOne);
        hh.deleteHabit(1, 1);
        assertNull(hh.getHabit(1));
    }

    @Test
    void getUserHabits() {
        hh.createHabit(habitOne);
        hh.createHabit(habitTwo);
        assertEquals(2,hh.getUserHabits(1).size());
    }
}