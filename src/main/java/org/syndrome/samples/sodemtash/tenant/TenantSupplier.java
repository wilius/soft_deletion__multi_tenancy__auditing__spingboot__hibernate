package org.syndrome.samples.sodemtash.tenant;

import java.util.UUID;

public class TenantSupplier {

    private static final ThreadLocal<UUID> TENANTIDS = new InheritableThreadLocal<>();

    private TenantSupplier() {
    }

    public static UUID get() {
        return TENANTIDS.get();
    }

    public static void set(UUID context) {
        TENANTIDS.set(context);
    }

    public static void clear() {
        TENANTIDS.remove();
    }

}