package kira.notification.application.port.in.resetprogress;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.domain.ResetType;
import kira.notification.domain.marker.User;

public record ResetProgressNotification(ResetType resourceType, String name, List<Id<User>> studentIds) {}
