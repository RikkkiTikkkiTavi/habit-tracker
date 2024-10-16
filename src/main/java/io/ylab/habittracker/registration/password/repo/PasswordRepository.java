package io.ylab.habittracker.registration.password.repo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PasswordRepository {

    private final Map<Long, String> userPasswords = new HashMap<>();

    public void addPassword(long userId, String password) {
        userPasswords.put(userId, encodePassword(password));
    }

    public boolean isPassword(long userId, String password) {
        return userPasswords.get(userId).equals(encodePassword(password));
    }

    private String encodePassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = Objects.requireNonNull(md).digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
