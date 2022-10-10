package com.udacity.jwdnd.course1.cloudstorage.domain.repository;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
class H2MyBatisRepositoryIntegrationTest {

    private User dummyUser;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        dummyUser = new User();
        dummyUser.setFirstName("Johny");
        dummyUser.setLastName("Bravo");
        dummyUser.setUsername("johny_bravo");
        dummyUser.setPassword("totally_secured_password");
    }

    @Test
    void testSaveAndRetrieve() {
        userRepository.save(dummyUser);
        User retrievedUser = userRepository.findByUsername("johny_bravo");

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUsername("johny_bravo");
        expectedUser.setPassword("totally_secured_password");
        expectedUser.setFirstName("Johny");
        expectedUser.setLastName("Bravo");

        assertEquals(expectedUser, retrievedUser);
    }
}