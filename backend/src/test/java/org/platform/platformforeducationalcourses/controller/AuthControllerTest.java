package org.platform.platformforeducationalcourses.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.dto.auth.AuthResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import refactor.user.adapter.in.web.TokenUpdateController;
import refactor.user.adapter.out.token.TokenProperties;
import refactor.user.application.ports.in.command.LoginCommand;
import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.command.RegistrationCommand;
import refactor.user.application.service.LoginService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginService loginService;

    @Mock
    private TokenProperties configuration;

    @InjectMocks
    private TokenUpdateController authController;

    @Test
    void userRegistration_ReturnsCreatedAndCookie() {
        RegistrationCommand request = mock(RegistrationCommand.class);
        PairOfTokens mockTokenDto = mock(PairOfTokens.class);
        when(mockTokenDto.refreshToken()).thenReturn("dummy-refresh-token");
        when(loginService.registration(request)).thenReturn(mockTokenDto);

        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.userRegistration(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).contains("dummy-refresh-token"));
    }

    @Test
    void userLogin_ReturnsOkAndCookie() {
        LoginCommand request = mock(LoginCommand.class);
        PairOfTokens mockTokenDto = mock(PairOfTokens.class);
        when(mockTokenDto.refreshToken()).thenReturn("dummy-refresh-token");
        when(loginService.login(request)).thenReturn(mockTokenDto);
        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.userLogin(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
    }

    @Test
    void refresh_ReturnsOkAndCookie() {
        String oldToken = "old-token";
        PairOfTokens mockTokenDto = mock(PairOfTokens.class);
        when(mockTokenDto.refreshToken()).thenReturn("new-refresh-token");
        when(loginService.refresh(oldToken)).thenReturn(mockTokenDto);
        when(configuration.refreshTtl()).thenReturn(Duration.of(3600, ChronoUnit.MILLIS));

        ResponseEntity<AuthResponse> response = authController.refresh(oldToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).contains("new-refresh-token"));
    }
}
