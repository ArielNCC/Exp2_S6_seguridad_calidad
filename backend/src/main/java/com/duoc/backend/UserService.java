package com.duoc.backend;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationRequest request) {
        validateRequest(request);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null || isBlank(rawPassword) || isBlank(user.getPassword())) {
            return false;
        }

        if (isPasswordHashed(user.getPassword())) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }

        if (!user.getPassword().equals(rawPassword)) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return true;
    }

    public int migratePlainTextPasswords() {
        int migratedUsers = 0;

        for (User user : userRepository.findAll()) {
            String password = user.getPassword();
            if (isBlank(password) || isPasswordHashed(password)) {
                continue;
            }

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            migratedUsers++;
        }

        return migratedUsers;
    }

    public boolean isPasswordHashed(String password) {
        return password != null && BCRYPT_PATTERN.matcher(password).matches();
    }

    private void validateRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        if (isBlank(request.getUsername()) || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Username, email and password are required");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}