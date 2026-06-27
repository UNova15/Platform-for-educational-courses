package org.platform.platformforeducationalcourses.service;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.ports.persistance.UserRepository;
import org.platform.platformforeducationalcourses.domain.ports.security.SecurityPort;
import org.platform.platformforeducationalcourses.domain.user.Login;
import org.platform.platformforeducationalcourses.domain.user.Password;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginDto;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationDto;
import org.platform.platformforeducationalcourses.exception.AuthenticationException;
import org.platform.platformforeducationalcourses.exception.UserAlreadyExistException;
import org.platform.platformforeducationalcourses.security.hash.PasswordHasher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordHasher encoder;
    private final SecurityPort authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public TokenDto registration(RegistrationDto request) {
        if (userRepository.findByLogin(request.login()).isPresent()) {
            throw new UserAlreadyExistException(request.login());
        }

        Login login = Login.create(request.login());
        Password password = Password.create(request.password(), encoder);
        User user = User.createNew(login, password, request.role());

        long userId = userRepository.save(user).getId();

        return tokenService.createTokens(userId, request.login(), request.role());
    }

    public TokenDto login(LoginDto request) {
        User user = authenticationManager.identify(request.login(), request.password());

        return tokenService.createTokens(user.getId(), user.getLogin(), user.getRole());
    }

    public TokenDto refresh(String refreshToken) {
        if (!tokenService.isValidRefreshToken(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token");
        }

        return tokenService.recreateTokens(refreshToken);
    }
}
