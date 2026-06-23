package org.platform.platformforeducationalcourses.service;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.dto.auth.LoginRequest;
import org.platform.platformforeducationalcourses.dto.auth.RegistrationRequest;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.exception.UserAlreadyExistException;
import org.platform.platformforeducationalcourses.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public TokenDto registration(RegistrationRequest request) {
        if (userRepository.findByLogin(request.login()).isPresent()) {
            throw new UserAlreadyExistException(request.login());
        }

        User user = User.createNew(request.login(), request.password(), request.role(), encoder);
        long userId = userRepository.save(user).getId();

        return tokenService.createTokens(userId, request.login(), request.role());
    }

    public TokenDto login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        return tokenService.createTokens(user.getId(), user.getUsername(), user.getRole());
    }

    public TokenDto refresh(String refreshToken) {

        if (!tokenService.isValidRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Некорректный refresh - токен");
        }

        return tokenService.recreateTokens(refreshToken);
    }
}
