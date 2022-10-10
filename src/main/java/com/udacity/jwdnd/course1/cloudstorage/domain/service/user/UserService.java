package com.udacity.jwdnd.course1.cloudstorage.domain.service.user;

public interface UserService {
    void createUser(
            String firstName,
            String lastName,
            String userName,
            String password
    ) throws UserException;
}
