package io.ylab.habittracker.user.repo;

public final class UserRepoManager {
    private UserRepoManager() {
    }

    private static UserRepository ur;

    public static UserRepository getInstance() {
        if (ur == null) {
            ur = new InMemoryUserRepository();
        }
        return ur;
    }
}


