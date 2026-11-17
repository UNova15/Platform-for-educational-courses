package kira.auth.application.ports.out.persistance.query.internal;

import java.util.List;
import java.util.Optional;

public interface UserProfileProviderPort {
    List<String> getUsersEmailByIds(List<Long> ids);

    Optional<String> getUserNameById(long userId);
}
