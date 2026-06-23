package org.platform.platformforeducationalcourses.service;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.security.RefreshToken;
import org.platform.platformforeducationalcourses.domain.user.Role;
import org.platform.platformforeducationalcourses.dto.auth.ParsedToken;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.repository.RefreshTokenRepository;
import org.platform.platformforeducationalcourses.tokenutil.TokenGenerator;
import org.platform.platformforeducationalcourses.tokenutil.TokenHasher;
import org.platform.platformforeducationalcourses.tokenutil.TokenUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenGenerator generator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHasher hasher;
    private final TokenUtil tokenUtil;

    public TokenDto createTokens(long userId, String login, Role role) {
        String jwt = generator.generateJwtToken(userId, login, role);
        String refresh = generator.generateRefreshToken(userId, login, role);

        RefreshToken refreshToken = RefreshToken.createNew(userId, refresh, hasher);
        refreshTokenRepository.save(refreshToken);

        return new TokenDto(userId, login, role, jwt, refresh);
    }

    @Transactional
    public TokenDto recreateTokens(String oldRawRefreshToken) {

        if (!tokenUtil.isValidToken(oldRawRefreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String oldHashedToken = hasher.hash(oldRawRefreshToken);
        RefreshToken tokenFromDb = refreshTokenRepository.findByToken(oldHashedToken)
                .orElseThrow(() -> new BadCredentialsException("Refresh token not found in DB"));

        refreshTokenRepository.delete(tokenFromDb);
        long userId = tokenFromDb.getUserId();

        ParsedToken parsedToken = tokenUtil.parseToken(oldRawRefreshToken);

        String newRawJwt = generator.generateJwtToken(userId, parsedToken.login(), parsedToken.role());
        String newRawRefreshToken = generator.generateRefreshToken(parsedToken.userId(), parsedToken.login(), parsedToken.role());

        RefreshToken newRefreshToken = RefreshToken.createNew(parsedToken.userId(), newRawRefreshToken, hasher);

        refreshTokenRepository.save(newRefreshToken);

        return new TokenDto(parsedToken.userId(), parsedToken.login(), parsedToken.role(), newRawJwt, newRawRefreshToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        if (!tokenUtil.isValidToken(refreshToken)) {
            return false;
        }
        long userId = tokenUtil.parseUserIdFromToken(refreshToken);
        String hashedRefreshToken = hasher.hash(refreshToken);

        return refreshTokenRepository.findByTokenAndUserId(hashedRefreshToken, userId)
                .isPresent();
    }
}
