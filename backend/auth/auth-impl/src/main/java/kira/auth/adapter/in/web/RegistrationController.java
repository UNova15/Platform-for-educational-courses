package kira.auth.adapter.in.web;

import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kira.auth.application.ports.in.AuthResult;
import kira.auth.application.ports.in.RegistrationUseCase;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class RegistrationController {
    private final RegistrationUseCase registrationService;
    private final CookieFactory cookieFactory;

    @PostMapping("/registration")
    public ResponseEntity<AuthResult> userRegistration(@RequestBody @Valid RegistrationCommand request) {
        Login login = Login.of(request.login());
        RawPassword password = RawPassword.of(request.password());

        AuthResult authResult = registrationService.registration(login, password, request.role());

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(authResult);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(authResult);
    }
}
