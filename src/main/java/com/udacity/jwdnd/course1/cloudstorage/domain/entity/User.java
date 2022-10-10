package com.udacity.jwdnd.course1.cloudstorage.domain.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
