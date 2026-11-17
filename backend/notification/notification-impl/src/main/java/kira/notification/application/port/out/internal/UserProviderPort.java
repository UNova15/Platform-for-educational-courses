package kira.notification.application.port.out.internal;

import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.notification.domain.marker.User;

public interface UserProviderPort {
    List<String> getEmailByIds(List<Id<User>> ids);

    Optional<String> getNameById(Id<User> userId);
}
