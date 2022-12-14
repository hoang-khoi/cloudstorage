package com.udacity.jwdnd.course1.cloudstorage.domain.repository.user;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2MyBatisUserRepositoryTest {

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
