package kira.notification.adapter.out.external;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.application.port.out.external.UserProviderPort;
import kira.notification.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProviderAdapter implements UserProviderPort {

    // TODO заглушка
    @Override
    public List<String> getEmailByIds(List<Id<User>> ids) {
        return List.of("example@mail.ru");
    }
}
