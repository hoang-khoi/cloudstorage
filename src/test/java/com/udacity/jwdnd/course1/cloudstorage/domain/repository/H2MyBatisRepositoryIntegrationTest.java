package com.udacity.jwdnd.course1.cloudstorage.domain.repository;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void testSaveAndFindByUsername_BasicFunctionality() {
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

    @Test
    void testFindBindUserName_NotFound_ReturnNull() {
        User retrievedUser = userRepository.findByUsername("johny_bravo");
        assertNull(retrievedUser);
    }
}
