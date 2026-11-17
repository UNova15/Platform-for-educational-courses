package kira.user.adapter.out.hashing;

import kira.user.application.ports.out.crypto.PasswordHasherPort;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PasswordHasher implements PasswordHasherPort {
    private final PasswordEncoder encoder;

    @Override
    public HashedPassword hashPassword(RawPassword password) {
        return HashedPassword.of(encoder.encode(password.value()));
    }

    @Override
    public boolean matches(RawPassword password, HashedPassword hashedPassword) {
        return encoder.matches(password.value(), hashedPassword.value());
    }
}
