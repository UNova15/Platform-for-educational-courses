package kira.notification.adapter.out.render;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.EnumMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalMessageTemplateLoader {
    private final ResourceLoader loader;

    private final EnumMap<EmailType, String> templates = new EnumMap<>(EmailType.class);

    @PostConstruct
    public void load() throws IOException {
        for (var emailType : EmailType.values()) {
            Resource resource = loader.getResource(emailType.path());
            String content = resource.getContentAsString(Charset.defaultCharset());

            templates.put(emailType, content);
        }
    }

    public String getTemplate(EmailType templateName) {
        var template = templates.get(templateName);
        if (template == null) {
            throw new IllegalStateException(
                    "Email template with courseTitle: %s not found".formatted(templateName.name()));
        }
        return template;
    }
}
