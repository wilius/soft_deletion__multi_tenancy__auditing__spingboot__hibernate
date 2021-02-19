package org.syndrome.samples.sodemtash.data.entity.base;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.syndrome.samples.sodemtash.tenant.TenantSupplier;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
@FilterDef(
        name = TenantBasedEntity.DEFAULT_FILTER,
        parameters = {
                @ParamDef(name = "tenantId", type = "java.util.UUID")
        },
        defaultCondition = "tenant_id = :tenantId"
)
@Filter(name = TenantBasedEntity.DEFAULT_FILTER)
public abstract class TenantBasedEntity extends BaseEntity implements TenantAwareEntity {
    public static final String DEFAULT_FILTER = "TenantBasedEntityFilter";

    private UUID tenantId;

    @PrePersist
    protected void onPrePersist() {
        this.tenantId = TenantSupplier.get();
    }

    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
