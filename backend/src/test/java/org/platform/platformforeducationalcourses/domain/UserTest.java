package org.platform.platformforeducationalcourses.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.platform.platformforeducationalcourses.domain.user.Role;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserTest {
    private final Role DUMMY_ROLE = null;

    @Test
    void createNew_Success() {
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        when(mockEncoder.encode("strongPassword")).thenReturn("encodedPassword");

        User user = User.createNew("john_doe", "strongPassword", DUMMY_ROLE, mockEncoder);

        assertNotNull(user);
        assertEquals("john_doe", user.getLogin());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(DUMMY_ROLE, user.getRole());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void createNew_ThrowsException_WhenLoginIsInvalid(String invalidLogin) {
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        assertThrows(
                IllegalArgumentException.class,
                () -> User.createNew(invalidLogin, "password123", DUMMY_ROLE, mockEncoder));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "short"})
    void createNew_ThrowsException_WhenPasswordIsInvalid(String invalidPassword) {
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        assertThrows(
                IllegalArgumentException.class,
                () -> User.createNew("login", invalidPassword, DUMMY_ROLE, mockEncoder));
    }
}
