package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.repository.DbRepositoryTest;
import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DbHistoryRepositoryTest extends DbRepositoryTest {

    @Test
    void addNewUserHistoryIncreaseHistorySizeByOne() {
        assertEquals(1, historyRepository
                .getUserHistory(1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)).size()
        );
    }

    @Test
    void addUserHistoryCreatedHistoryWithStreakEquals1() {
        assertEquals(1, historyRepository.getHistory(1, LocalDate.now()).getStreak());
    }
    @Test
    void addUserHistoryWithNonExistentUserThrowsException() {
        habitHistory.setUserId(100);
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> historyRepository.addUserHistory(habitHistory));

    }


    @Test
    void getExistUserHistoryReturnHistory() {
        assertEquals(HabitHistory.class, historyRepository.getHistory(1, LocalDate.now()).getClass());
    }

    @Test
    void getUserHistoryReturnsListOfUserHistory() {
        assertEquals(1, historyRepository.getUserHistory(1, LocalDate.now(), LocalDate.now().plusDays(1)).size());
    }

    @Test
    void getLastNoteDateReturnDateOfCreatingHabitOne() {
        assertEquals(habitOne.getCreateTime(), historyRepository.getLastNoteDate(1));
    }
}