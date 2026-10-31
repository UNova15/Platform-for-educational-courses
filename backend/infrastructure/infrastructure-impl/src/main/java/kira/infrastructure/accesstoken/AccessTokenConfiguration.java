package kira.infrastructure.accesstoken;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AccessTokenProperties.class)
class AccessTokenConfiguration {

    @Bean
    public JwtTokenProcessor jwtTokenDecoder(AccessTokenProperties properties) {
        return JwtTokenProcessor.createWithKey(
                properties.accessTokenGenerationKey(), properties.accessTokenClockSkew(), properties.accessTokenTtl());
    }
}
