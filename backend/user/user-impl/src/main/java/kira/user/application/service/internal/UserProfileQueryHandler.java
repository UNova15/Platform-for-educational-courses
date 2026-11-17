package kira.user.application.service.internal;

import java.util.List;
import java.util.Optional;
import kira.user.api.UserProviderQueryPort;
import kira.user.application.ports.out.persistance.query.internal.UserProfileProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileQueryHandler implements UserProviderQueryPort {
    private final UserProfileProviderPort userProfileProviderPort;

    @Override
    public List<String> getUsersEmailByIds(List<Long> ids) {
        return userProfileProviderPort.getUsersEmailByIds(ids);
    }

    @Override
    public Optional<String> getUserNameById(long userId) {
        return userProfileProviderPort.getUserNameById(userId);
    }
}
