package kira.notification.adapter.out.internal;

import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.notification.application.port.out.internal.UserProviderPort;
import kira.notification.domain.marker.User;
import kira.user.api.UserProviderQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProviderAdapter implements UserProviderPort {
    private final UserProviderQueryPort userProviderQueryPort;

    @Override
    public List<String> getEmailByIds(List<Id<User>> ids) {

        return userProviderQueryPort.getUsersEmailByIds(
                ids.stream().map(Id::value).toList());
    }

    @Override
    public Optional<String> getNameById(Id<User> userId) {
        return userProviderQueryPort.getUserNameById(userId.value());
    }
}
