package org.platform.platformforeducationalcourses.controller.authentication;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.dto.auth.*;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginRequest;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationRequest;
import org.platform.platformforeducationalcourses.mapper.AuthMapper;
import org.platform.platformforeducationalcourses.service.AuthService;
import org.platform.platformforeducationalcourses.util.auth.AuthCookieFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthMapper mapper;
    private final AuthService authService;
    private final AuthCookieFactory cookieFactory;

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> userRegistration(@RequestBody @Valid RegistrationRequest request) {
        TokenDto tokenDto = authService.registration(mapper.toRegistrationDto(request));
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(tokenDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody @Valid LoginRequest request) {
        TokenDto tokenDto = authService.login(mapper.toLoginDto(request));
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(tokenDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        TokenDto tokenDto = authService.refresh(refreshToken);
        AuthResponse response = new AuthResponse(tokenDto);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(tokenDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }
}
