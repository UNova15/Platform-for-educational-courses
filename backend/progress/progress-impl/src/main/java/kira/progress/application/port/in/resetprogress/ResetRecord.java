package kira.progress.application.port.in.resetprogress;

import common.domain.entity.Id;
import kira.progress.event.ResetProgressEvent;

public record ResetRecord(
        ResetProgressEvent.ResourceType resourceType,
        ResetProgressEvent.ResetReason action,
        String name,
        Id<?> resourceId) {}
