package com.udacity.jwdnd.course1.cloudstorage.domain.service.user;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.domain.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Captor
    ArgumentCaptor<User> userCaptor;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        //noinspection resource
        MockitoAnnotations.openMocks(this);

        underTest = new UserServiceImpl(
                mockedUserRepository,
                mockedPasswordEncoder
        );
    }

    @Test
    void testCreateUser_BlankFirstName_UserExceptionThrown() {
        Exception ex = assertThrows(UserException.class, () -> underTest.createUser(" ", "Hoang", "hotel_kilo", "pwd"));
        assertEquals("Invalid user input.", ex.getMessage());
    }

    @Test
    void testCreateUser_BlankLastName_UserExceptionThrown() {
        Exception ex = assertThrows(UserException.class, () -> underTest.createUser("Khoi", "  ", "hotel_kilo", "pwd"));
        assertEquals("Invalid user input.", ex.getMessage());
    }

    @Test
    void testCreateUser_BlankUserName_UserExceptionThrown() {
        Exception ex = assertThrows(UserException.class, () -> underTest.createUser("Khoi", "Hoang", "  ", "pwd"));
        assertEquals("Invalid user input.", ex.getMessage());
    }

    @Test
    void testCreateUser_WeakPassword_UserExceptionThrown() {
        Exception ex = assertThrows(UserException.class, () -> underTest.createUser("Khoi", "Hoang", "hoang_khoi", "1234567"));
        assertEquals("Weak password.", ex.getMessage());
    }

    @Test
    void testCreateUser_UsernameAlreadyExisted_UserExceptionThrown() {
        when(mockedUserRepository.findByUsername("existed")).thenReturn(new User());

        Exception ex = assertThrows(UserException.class, () -> underTest.createUser("khoi", "hoang", "existed", "12345678"));
        assertEquals("Username already existed.", ex.getMessage());
    }

    @Test
    void testCreateUser_Success() throws UserException {
        when(mockedUserRepository.findByUsername("existed")).thenReturn(null);
        when(mockedPasswordEncoder.encode("12345678")).thenReturn("87654321");

        underTest.createUser("khoi", "hoang", "khoi_hoang", "12345678");
        verify(mockedUserRepository).save(userCaptor.capture());

        User expectedUser = new User();
        expectedUser.setUsername("khoi_hoang");
        expectedUser.setPassword("87654321");
        expectedUser.setFirstName("khoi");
        expectedUser.setLastName("hoang");

        assertEquals(expectedUser, userCaptor.getValue());
    }
}
