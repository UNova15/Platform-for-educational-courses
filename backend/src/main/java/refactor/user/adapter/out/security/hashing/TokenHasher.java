package refactor.user.adapter.out.security.hashing;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import refactor.user.application.ports.out.token.TokenHashingPort;
import refactor.user.domain.token.HashedToken;
import refactor.user.domain.token.RawToken;

@Component
public class TokenHasher implements TokenHashingPort {

    @Override
    public HashedToken hash(RawToken token) {
        return HashedToken.of(DigestUtils.sha256Hex(token.getToken()));
    }
}
