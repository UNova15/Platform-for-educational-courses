package auth.adapter.out.hashing;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.implemetnation.domain.user.valueobject.HashedPassword;
import refactor.auth.implemetnation.domain.user.valueobject.RawPassword;

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
