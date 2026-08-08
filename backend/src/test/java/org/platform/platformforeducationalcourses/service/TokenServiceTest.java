package org.platform.platformforeducationalcourses.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.security.entity.SecurityRole;
import org.platform.platformforeducationalcourses.util.token.TokenGenerator;
import org.platform.platformforeducationalcourses.util.token.TokenUtil;
import org.springframework.security.authentication.BadCredentialsException;
import refactor.auth.adapter.out.persistence.token.OrmRefreshTokenRepository;
import refactor.auth.adapter.out.persistence.token.RefreshTokenEntity;
import refactor.auth.adapter.out.security.hashing.TokenHasher;
import refactor.auth.application.ports.in.usecase.PairOfTokens;
import refactor.infrastructure.accesstoken.TokenPayload;
import refactor.auth.application.service.TokenService;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private TokenGenerator generator;

    @Mock
    private OrmRefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenHasher hasher;

    @Mock
    private TokenUtil tokenUtil;

    @InjectMocks
    private TokenService tokenService;

    private final SecurityRole DUMMY_ROLE = null;

    @Test
    void createTokens_Success() {
        when(generator.generateJwtToken(1L, "user", DUMMY_ROLE)).thenReturn("jwt");
        when(generator.generateRefreshToken(1L, "user", DUMMY_ROLE)).thenReturn("refresh");
        when(hasher.hash("refresh")).thenReturn("hashed_refresh");

        PairOfTokens result = tokenService.createTokens(1L, "user", DUMMY_ROLE);

        assertEquals("jwt", result.accessToken());
        assertEquals("refresh", result.refreshToken());
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
    }

    @Test
    void recreateTokens_Success() {
        String oldRefresh = "old_refresh";
        when(tokenUtil.isValidToken(oldRefresh)).thenReturn(true);
        when(hasher.hash(oldRefresh)).thenReturn("hashed_old");

        RefreshTokenEntity mockTokenFromDb = mock(RefreshTokenEntity.class);
        when(mockTokenFromDb.getUserId()).thenReturn(1L);
        when(refreshTokenRepository.findByToken("hashed_old")).thenReturn(Optional.of(mockTokenFromDb));

        TokenPayload tokenPayload = mock(TokenPayload.class);
        when(tokenPayload.userId()).thenReturn(1L);
        when(tokenPayload.login()).thenReturn("user");
        when(tokenPayload.role()).thenReturn(DUMMY_ROLE);
        when(tokenUtil.parseToken(oldRefresh)).thenReturn(tokenPayload);

        when(generator.generateJwtToken(1L, "user", DUMMY_ROLE)).thenReturn("new_jwt");
        when(generator.generateRefreshToken(1L, "user", DUMMY_ROLE)).thenReturn("new_refresh");

        PairOfTokens result = tokenService.recreateTokens(oldRefresh);

        assertNotNull(result);
        verify(refreshTokenRepository).delete(mockTokenFromDb);
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
    }

    @Test
    void recreateTokens_ThrowsException_WhenTokenInvalid() {
        when(tokenUtil.isValidToken("invalid")).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> tokenService.recreateTokens("invalid"));
    }

    @Test
    void isValidRefreshToken_ReturnsTrue_WhenValidAndExistsInDb() {
        when(tokenUtil.isValidToken("token")).thenReturn(true);
        when(tokenUtil.parseUserIdFromToken("token")).thenReturn(1L);
        when(hasher.hash("token")).thenReturn("hashed");
        when(refreshTokenRepository.findByTokenAndUserId("hashed", 1L))
                .thenReturn(Optional.of(mock(RefreshTokenEntity.class)));

        assertTrue(tokenService.isValidRefreshToken("token"));
    }

    @Test
    void isValidRefreshToken_ReturnsFalse_WhenInvalid() {
        when(tokenUtil.isValidToken("invalid")).thenReturn(false);
        assertFalse(tokenService.isValidRefreshToken("invalid"));
    }
}
