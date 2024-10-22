package io.ylab.habittracker.repository;

import io.ylab.habittracker.LiquibaseScripts;
import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.habit.DbHabitRepository;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.repository.history.DbHistoryRepository;
import io.ylab.habittracker.repository.history.HistoryRepository;
import io.ylab.habittracker.repository.role.DbUserRoleRepository;
import io.ylab.habittracker.repository.role.RoleRepository;
import io.ylab.habittracker.repository.user.DbUserRepository;
import io.ylab.habittracker.repository.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

public class DbRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    public HistoryRepository historyRepository;
    public HabitRepository habitRepository;
    public UserRepository userRepository;
    public RoleRepository roleRepository;
    public Habit habitOne;
    public Habit habitTwo;
    public HabitHistory habitHistory;
    public User userOne;

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

        habitRepository = new DbHabitRepository(connectionProvider);
        roleRepository = new DbUserRoleRepository(connectionProvider);
        userRepository = new DbUserRepository(connectionProvider, roleRepository);
        historyRepository = new DbHistoryRepository(connectionProvider);

        userOne = new User("1","email");
        userOne.setPassword("password");
        userRepository.addUser(userOne);

        habitOne = new Habit("1", "1", Frequency.EVERY_DAY, 1L);
        habitTwo = new Habit("2", "2", Frequency.EVERY_DAY, 1L);

        habitRepository.createHabit(habitOne);
        habitRepository.createHabit(habitTwo);

        habitHistory = new HabitHistory(1,1, LocalDate.now());
        historyRepository.addUserHistory(habitHistory);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteUser("email");
    }
}