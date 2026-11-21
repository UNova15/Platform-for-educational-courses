package kira.user.adapter.out.codegenerator;

import java.security.SecureRandom;
import kira.user.application.ports.out.crypto.ConfirmCodeGenerationPort;
import kira.user.domain.code.valueobject.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConfirmCodeGenerator implements ConfirmCodeGenerationPort {
    private static final int MAX_DIGIT_COUNT = 10;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public Code generate() {
        var code = new StringBuilder(Code.CODE_LENGTH);

        for (int i = 0; i < Code.CODE_LENGTH; i++) {
            code.append(secureRandom.nextInt(MAX_DIGIT_COUNT));
        }

        return Code.of(code.toString());
    }
}
