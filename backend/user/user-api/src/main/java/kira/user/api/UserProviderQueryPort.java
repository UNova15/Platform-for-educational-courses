package kira.user.api;

import java.util.List;
import java.util.Optional;

public interface UserProviderQueryPort {
    List<String> getUsersEmailByIds(List<Long> ids);

    Optional<String> getUserNameById(long userId);
}
