package kira.auth.adapter.out.hashing;

import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import kira.auth.application.ports.out.crypto.TokenHashingPort;

@Component
class TokenHasher implements TokenHashingPort {

    @Override
    public HashedRefreshToken hash(RawRefreshToken token) {
        return HashedRefreshToken.of(DigestUtils.sha256Hex(token.value()));
    }
}
