package org.syndrome.samples.sodemtash.data.entity.base;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.syndrome.samples.sodemtash.tenant.TenantSupplier;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
@FilterDef(
        name = TenantBasedAuditedEntity.DEFAULT_FILTER,
        parameters = {
                @ParamDef(name = "tenantId", type = "java.util.UUID")
        },
        defaultCondition = "tenant_id = :tenantId"
)
@Filter(name = TenantBasedAuditedEntity.DEFAULT_FILTER)
@EntityListeners(AuditingEntityListener.class)
public abstract class TenantBasedAuditedEntity extends BaseEntity implements TenantAwareEntity {
    public static final String DEFAULT_FILTER = "TenantBasedAuditedEntityFilter";
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
