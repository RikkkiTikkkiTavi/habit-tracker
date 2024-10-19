package io.ylab.habittracker.service.registration;

import io.ylab.habittracker.model.user.Role;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.repository.role.RoleManager;
import io.ylab.habittracker.repository.role.RoleRepository;
import io.ylab.habittracker.validate.Validate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class Registration {

    private final RoleRepository roleRepository = RoleManager.getInstance();

    public boolean authentication(User user, String password) {
        Validate.checkPassword(password);
        password = encodePassword(password);
        return user.getPassword().equals(password);
    }

    public void registration(User user, String password) {
        Validate.checkPassword(password);
        user.setPassword(encodePassword(password));
    }

    public Role authorization(long userId) {
        return roleRepository.getUserRole(userId);
    }

    private String encodePassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        byte[] hash = Objects.requireNonNull(md).digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}