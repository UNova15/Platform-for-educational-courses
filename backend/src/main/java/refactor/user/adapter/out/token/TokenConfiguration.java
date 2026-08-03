package refactor.user.adapter.out.token;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refactor.user.adapter.out.token.jwt.JwtTokenDecoder;
import refactor.user.adapter.out.token.jwt.JwtTokenGenerator;
import refactor.user.adapter.out.token.refresh.RefreshTokenGenerator;

@Configuration
public class TokenConfiguration {

    @Bean
    public JwtTokenGenerator jwtTokenGenerator(TokenProperties tokenProperties) {
        return JwtTokenGenerator.createWithKey(
                tokenProperties.accessTokenTtl(), tokenProperties.accessTokenGenerationKey());
    }

    @Bean
    public RefreshTokenGenerator refreshTokenGenerator(TokenProperties tokenProperties) {
        return new RefreshTokenGenerator(
                new SecureRandom(), Base64.getEncoder().withoutPadding(), tokenProperties.refreshTokenLength());
    }

    @Bean
    public JwtTokenDecoder jwtTokenDecoder(TokenProperties properties) {
        return JwtTokenDecoder.createWithKey(properties.accessTokenGenerationKey(), properties.accessTokenClockSkew());
    }
}
