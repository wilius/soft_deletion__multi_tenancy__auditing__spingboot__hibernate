package org.syndrome.samples.sodemtash.event;

import java.util.UUID;

public class BaseEvent {

    private UUID tenantId;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
