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
        name = TenantBasedSoftDeletedAuditedEntity.DEFAULT_FILTER,
        parameters = {
                @ParamDef(name = "deleted", type = "boolean"),
                @ParamDef(name = "tenantId", type = "java.util.UUID")
        },
        defaultCondition = "deleted = :deleted and tenant_id = :tenantId"
)
@Filter(name = TenantBasedSoftDeletedAuditedEntity.DEFAULT_FILTER)
@EntityListeners(AuditingEntityListener.class)
public abstract class TenantBasedSoftDeletedAuditedEntity extends BaseEntity implements TenantAwareEntity, SoftDeletableEntity {
    public static final String DEFAULT_FILTER = "TenantBasedSoftDeletedAuditedEntityFilter";
    private Boolean deleted;

    private UUID tenantId;

    @PrePersist
    protected void onPrePersist() {
        this.tenantId = TenantSupplier.get();
        this.deleted = false;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
