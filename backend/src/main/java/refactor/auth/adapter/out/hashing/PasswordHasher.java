package refactor.auth.adapter.out.hashing;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import refactor.auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.domain.user.valueobject.HashedPassword;
import refactor.auth.domain.user.valueobject.RawPassword;

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
