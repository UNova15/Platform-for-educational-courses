package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.platform.platformforeducationalcourses.domain.security.RefreshToken;
import org.platform.platformforeducationalcourses.tokenutil.TokenHasher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenTest {

    @Test
    void createNew_Success() {
        TokenHasher mockHasher = mock(TokenHasher.class);
        when(mockHasher.hash("raw-token")).thenReturn("hashed-token");

        RefreshToken token = RefreshToken.createNew(1L, "raw-token", mockHasher);

        assertNotNull(token);
        assertEquals(1L, token.getUserId());
        assertEquals("hashed-token", token.getToken());
        verify(mockHasher, times(1)).hash("raw-token");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void createNew_ThrowsException_WhenTokenIsInvalid(String invalidToken) {
        TokenHasher mockHasher = mock(TokenHasher.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            RefreshToken.createNew(1L, invalidToken, mockHasher);
        });

        assertEquals("Incorrect token for user: 1", exception.getMessage());
    }
}