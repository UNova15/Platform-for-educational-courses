package refactor.user.adapter.in.web.controller;

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
import refactor.user.adapter.in.web.util.CookieFactory;
import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.command.RegistrationCommand;
import refactor.user.application.ports.in.usecase.RegistrationUseCase;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationUseCase registrationService;
    private final CookieFactory cookieFactory;

    @PostMapping("/registration")
    public ResponseEntity<PairOfTokens> userRegistration(@RequestBody @Valid RegistrationCommand request) {
        PairOfTokens pairOfTokens = registrationService.registration(request);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(pairOfTokens);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(pairOfTokens);
    }
}
