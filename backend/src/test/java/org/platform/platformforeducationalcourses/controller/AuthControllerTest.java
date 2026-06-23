package org.platform.platformforeducationalcourses.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.authentication.AuthController;
import org.platform.platformforeducationalcourses.dto.auth.AuthResponse;
import org.platform.platformforeducationalcourses.dto.auth.LoginRequest;
import org.platform.platformforeducationalcourses.dto.auth.RegistrationRequest;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.properties.TokenProperties;
import org.platform.platformforeducationalcourses.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private AuthService authService;
    @Mock private TokenProperties configuration;

    @InjectMocks private AuthController authController;

    @Test
    void userRegistration_ReturnsCreatedAndCookie() {
        RegistrationRequest request = mock(RegistrationRequest.class);
        TokenDto mockTokenDto = mock(TokenDto.class);
        when(mockTokenDto.refreshToken()).thenReturn("dummy-refresh-token");
        when(authService.registration(request)).thenReturn(mockTokenDto);

        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.userRegistration(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).contains("dummy-refresh-token"));
    }

    @Test
    void userLogin_ReturnsOkAndCookie() {
        LoginRequest request = mock(LoginRequest.class);
        TokenDto mockTokenDto = mock(TokenDto.class);
        when(mockTokenDto.refreshToken()).thenReturn("dummy-refresh-token");
        when(authService.login(request)).thenReturn(mockTokenDto);
        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.userLogin(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
    }

    @Test
    void refresh_ReturnsOkAndCookie() {
        String oldToken = "old-token";
        TokenDto mockTokenDto = mock(TokenDto.class);
        when(mockTokenDto.refreshToken()).thenReturn("new-refresh-token");
        when(authService.refresh(oldToken)).thenReturn(mockTokenDto);
        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.refresh(oldToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).contains("new-refresh-token"));
    }
}