package kira.user.adapter.out.hashing;

import kira.user.application.ports.out.crypto.TokenHashingPort;
import kira.user.domain.token.valueobject.HashedRefreshToken;
import kira.user.domain.token.valueobject.RawRefreshToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
class TokenHasher implements TokenHashingPort {

    @Override
    public HashedRefreshToken hash(RawRefreshToken token) {
        return HashedRefreshToken.of(DigestUtils.sha256Hex(token.value()));
    }
}
