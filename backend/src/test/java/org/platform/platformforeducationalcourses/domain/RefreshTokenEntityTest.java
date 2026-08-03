package org.platform.platformforeducationalcourses.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import refactor.user.adapter.out.persistence.token.RefreshTokenEntity;
import refactor.user.adapter.out.security.hashing.TokenHasher;

class RefreshTokenEntityTest {

    @Test
    void createNew_Success() {
        TokenHasher mockHasher = mock(TokenHasher.class);
        when(mockHasher.hash("raw-token")).thenReturn("hashed-token");

        RefreshTokenEntity token = RefreshTokenEntity.createNew(1L, "raw-token", mockHasher);

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
            RefreshTokenEntity.createNew(1L, invalidToken, mockHasher);
        });

        assertEquals("Incorrect token for user: 1", exception.getMessage());
    }
}
