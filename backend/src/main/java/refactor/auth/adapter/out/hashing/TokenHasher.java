package refactor.auth.adapter.out.hashing;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import refactor.auth.application.ports.out.crypto.TokenHashingPort;
import refactor.auth.domain.token.HashedToken;
import refactor.auth.domain.token.RawToken;

@Component
class TokenHasher implements TokenHashingPort {

    @Override
    public HashedToken hash(RawToken token) {
        return HashedToken.of(DigestUtils.sha256Hex(token.getToken()));
    }
}
