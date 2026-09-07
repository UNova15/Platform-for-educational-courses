package refactor.auth.adapter.out.hashing;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import refactor.auth.application.ports.out.crypto.TokenHashingPort;
import refactor.auth.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.domain.token.valueobject.RawRefreshToken;

@Component
class TokenHasher implements TokenHashingPort {

    @Override
    public HashedRefreshToken hash(RawRefreshToken token) {
        return HashedRefreshToken.of(DigestUtils.sha256Hex(token.value()));
    }
}
