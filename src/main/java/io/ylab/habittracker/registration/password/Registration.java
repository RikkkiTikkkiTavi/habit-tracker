package io.ylab.habittracker.registration.password;

import io.ylab.habittracker.registration.password.repo.PasswordRepository;
import io.ylab.habittracker.registration.role.Role;
import io.ylab.habittracker.registration.role.repo.UserRole;
import io.ylab.habittracker.validate.Validate;

public class Registration {
    private final PasswordRepository pr = new PasswordRepository();
    private final UserRole ur = new UserRole();

    public boolean authentication(long userId, String password) {
        Validate.checkPassword(password);
        return pr.isPassword(userId, password);
    }

    public void registration(long userId, String password) {
        Validate.checkPassword(password);
        pr.addPassword(userId, password);
        ur.addRole(userId, Role.USER);
    }

    public Role authorization(long userId) {
        return ur.getRole(userId);
    }

}
