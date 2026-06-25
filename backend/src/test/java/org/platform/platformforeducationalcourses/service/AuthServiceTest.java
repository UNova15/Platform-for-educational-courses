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
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.dto.auth.LoginRequest;
import org.platform.platformforeducationalcourses.dto.auth.RegistrationRequest;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.exception.UserAlreadyExistException;
import org.platform.platformforeducationalcourses.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registration_Success() {
        RegistrationRequest request = mock(RegistrationRequest.class);
        when(request.login()).thenReturn("testUser");
        when(request.password()).thenReturn("password123");
        when(request.role()).thenReturn(null); // Ваша Role

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.empty());
        when(encoder.encode("password123")).thenReturn("encodedPass");

        User savedUser = mock(User.class);
        when(savedUser.getId()).thenReturn(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        TokenDto expectedToken = mock(TokenDto.class);
        when(tokenService.createTokens(eq(1L), eq("testUser"), any())).thenReturn(expectedToken);

        TokenDto actualToken = authService.registration(request);

        assertEquals(expectedToken, actualToken);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registration_ThrowsException_WhenUserExists() {
        RegistrationRequest request = mock(RegistrationRequest.class);
        when(request.login()).thenReturn("existingUser");

        when(userRepository.findByLogin("existingUser")).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserAlreadyExistException.class, () -> authService.registration(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_Success() {
        LoginRequest request = mock(LoginRequest.class);
        when(request.login()).thenReturn("user");
        when(request.password()).thenReturn("pass");

        Authentication authentication = mock(Authentication.class);
        SecurityUser securityUser = mock(SecurityUser.class);
        when(securityUser.getId()).thenReturn(1L);
        when(securityUser.getUsername()).thenReturn("user");
        when(securityUser.getRole()).thenReturn(null);

        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        TokenDto expectedToken = mock(TokenDto.class);
        when(tokenService.createTokens(eq(1L), eq("user"), any())).thenReturn(expectedToken);

        TokenDto actualToken = authService.login(request);

        assertEquals(expectedToken, actualToken);
    }

    @Test
    void refresh_Success() {
        String token = "validRefresh";
        when(tokenService.isValidRefreshToken(token)).thenReturn(true);

        TokenDto expectedDto = mock(TokenDto.class);
        when(tokenService.recreateTokens(token)).thenReturn(expectedDto);

        TokenDto result = authService.refresh(token);
        assertEquals(expectedDto, result);
    }

    @Test
    void refresh_ThrowsException_WhenInvalid() {
        when(tokenService.isValidRefreshToken("invalid")).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> authService.refresh("invalid"));
    }
}
