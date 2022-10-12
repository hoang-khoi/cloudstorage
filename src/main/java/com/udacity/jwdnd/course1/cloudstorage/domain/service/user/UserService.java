package com.udacity.jwdnd.course1.cloudstorage.domain.service.user;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;

public interface UserService {
    void createUser(
            String firstName,
            String lastName,
            String userName,
            String password
    ) throws UserException;

    User getUserByUsername(String username);
}
