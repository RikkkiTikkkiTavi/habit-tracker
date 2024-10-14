package io.ylab.habittracker.history.repo;

import io.ylab.habittracker.history.model.UserHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryRepositoryTest {

    private HistoryRepository hs;
    private UserHistory uh;

    @BeforeEach
    void setUp() {
        hs = new InMemoryHistoryRepository();
        uh = new UserHistory();
        hs.addUserHistory(1,uh);
    }

    @Test
    void addUserHistorySaveHistoryInMemory() {
        assertEquals(uh, hs.getUserHistory(1));
    }

    @Test
    void getNonExistUserHistoryReturnNull() {
        assertNull(hs.getUserHistory(124));
    }
}