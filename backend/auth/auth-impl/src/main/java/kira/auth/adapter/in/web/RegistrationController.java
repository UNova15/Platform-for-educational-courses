package kira.auth.adapter.in.web;

import common.domain.Result;
import jakarta.validation.Valid;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.RegistrationUseCase;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class RegistrationController {
    private final RegistrationUseCase registrationService;
    private final CookieFactory cookieFactory;

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@RequestBody @Valid RegistrationCommand request) {
        Login login = Login.of(request.login());
        RawPassword password = RawPassword.of(request.password());

        Result<AuthTokens, RegistrationUseCase.Error> authTokens = registrationService.registration(login, password);

        return authTokens.fold(
                tokens -> ResponseEntity.status(HttpStatus.CREATED)
                        .header(
                                HttpHeaders.SET_COOKIE,
                                cookieFactory.createDefaultRefreshCookie(tokens).toString())
                        .body(tokens),
                failure -> switch (failure) {
                    case USER_ALREADY_EXISTS ->
                        ResponseEntity.status(HttpStatus.CONFLICT).body(failure);
                });
    }
}
