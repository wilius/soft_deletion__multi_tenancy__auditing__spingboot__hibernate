package org.syndrome.samples.sodemtash.data.entity.base;

import java.util.UUID;

public interface TenantAwareEntity {

    UUID getTenantId();
}
