package org.platform.platformforeducationalcourses.security.hash;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordHasher {
    private final PasswordEncoder encoder;

    public String hash(String password) {
        return encoder.encode(password);
    }
}
