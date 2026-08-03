package refactor.user.adapter.out.security.hashing;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import refactor.user.application.ports.out.user.PasswordHasherPort;
import refactor.user.domain.user.HashedPassword;
import refactor.user.domain.user.RawPassword;

@Component
@AllArgsConstructor
public class PasswordHasher implements PasswordHasherPort {
    private final PasswordEncoder encoder;

    @Override
    public HashedPassword hash(RawPassword password) {
        return HashedPassword.of(encoder.encode(password.getPassword()));
    }
}
