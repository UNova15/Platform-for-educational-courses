package kira.notification.application.port.out.external;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.domain.User;

public interface UserProviderPort {
    List<String> getEmailByIds(List<Id<User>> ids);
}
