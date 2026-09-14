package infrastructure.accesstoken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccessTokenConfiguration {

    @Bean
    public JwtTokenProcessor jwtTokenDecoder(AccessTokenProperties properties) {
        return JwtTokenProcessor.createWithKey(
                properties.accessTokenGenerationKey(), properties.accessTokenClockSkew(), properties.accessTokenTtl());
    }
}
