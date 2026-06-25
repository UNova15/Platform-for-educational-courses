package org.platform.platformforeducationalcourses.controller.authentication;

import jakarta.validation.Valid;
import org.platform.platformforeducationalcourses.dto.auth.*;
import org.platform.platformforeducationalcourses.properties.TokenProperties;
import org.platform.platformforeducationalcourses.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProperties configuration;

    @Autowired
    public AuthController(AuthService authService, TokenProperties configuration) {
        this.authService = authService;
        this.configuration = configuration;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> userRegistration(@RequestBody @Valid RegistrationRequest request) {
        TokenDto tokenDto = authService.registration(request);
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDto.refreshToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .secure(true)
                .maxAge(configuration.refreshTtl())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody @Valid LoginRequest request) {
        TokenDto tokenDto = authService.login(request);
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDto.refreshToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .secure(true)
                .maxAge(configuration.refreshTtl())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        TokenDto tokenDto = authService.refresh(refreshToken);
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDto.refreshToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .secure(true)
                .maxAge(configuration.refreshTtl())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }
}
