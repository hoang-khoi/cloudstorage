package com.udacity.jwdnd.course1.cloudstorage.domain.service.user;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(String firstName, String lastName, String userName, String password) throws UserException {
        if (firstName.isBlank() || lastName.isBlank() || userName.isBlank()) {
            // Very simple validation!
            throw new UserException("Invalid user input.");
        }

        if (password.length() < 8) {
            throw new UserException("Weak password.");
        }

        if (userRepository.findByUsername(userName) != null) {
            // User existed in the DB
            throw new UserException("Username already existed.");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
