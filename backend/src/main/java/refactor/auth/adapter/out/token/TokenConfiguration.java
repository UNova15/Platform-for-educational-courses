package refactor.auth.adapter.out.token;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refactor.infrastructure.accesstoken.AccessTokenGenerator;

@Configuration
class TokenConfiguration {

    @Bean
    public JwtTokenGenerator jwtTokenGenerator(AccessTokenGenerator generator) {
        return new JwtTokenGenerator(generator);
    }

    @Bean
    public RefreshTokenGenerator refreshTokenGenerator(RefreshTokenProperties tokenProperties) {
        return new RefreshTokenGenerator(
                new SecureRandom(), Base64.getEncoder().withoutPadding(), tokenProperties.refreshTokenLength());
    }
}
