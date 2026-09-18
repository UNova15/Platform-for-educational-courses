package kira.auth.adapter.out.tokengenerator;

import java.security.SecureRandom;
import java.util.Base64;

import kira.infrastructure.api.AccessTokenGenerator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RefreshTokenProperties.class)
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
