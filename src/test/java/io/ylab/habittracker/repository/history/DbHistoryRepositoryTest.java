package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.LiquibaseScripts;
import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.habit.DbHabitRepository;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.repository.role.DbUserRoleRepository;
import io.ylab.habittracker.repository.role.RoleRepository;
import io.ylab.habittracker.repository.user.DbUserRepository;
import io.ylab.habittracker.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DbHistoryRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    HistoryRepository historyRepository;
    HabitRepository hh;
    UserRepository uh;
    RoleRepository rr;
    Habit habitOne;
    Habit habitTwo;
    HabitHistory habitHistory;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        LiquibaseScripts.setChangeSet(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());
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

        hh = new DbHabitRepository(connectionProvider);
        rr = new DbUserRoleRepository(connectionProvider);
        uh = new DbUserRepository(connectionProvider, rr);
        historyRepository = new DbHistoryRepository(connectionProvider);

        User userOne = new User("1","email");
        userOne.setPassword("password");
        uh.addUser(userOne);

        habitOne = new Habit("1", "1", Frequency.EVERY_DAY, 1L);
        habitTwo = new Habit("2", "2", Frequency.EVERY_DAY, 1L);

        hh.createHabit(habitOne);
        hh.createHabit(habitTwo);

        habitHistory = new HabitHistory(1,1, LocalDate.now());
    }

    @Test
    void addNewUserHistoryIncreaseHistorySizeByOne() {
        historyRepository.addUserHistory(habitHistory);
        assertEquals(1, historyRepository
                .getUserHistory(1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)).size()
        );
    }

    @Test
    void addUserHistoryCreatedHistoryWithStreakEquals1() {
        historyRepository.addUserHistory(habitHistory);
        assertEquals(1, historyRepository.getHistory(1, LocalDate.now()).getStreak());
    }
    @Test
    void addUserHistoryWithNonExistentUserThrowsException() {

    }


    @Test
    void getUserHistory() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void getLastNoteDate() {
    }
}