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
        name = TenantBasedSoftDeletedEntity.DEFAULT_FILTER,
        parameters = {
                @ParamDef(name = "deleted", type = "boolean"),
                @ParamDef(name = "tenantId", type = "java.util.UUID")
        },
        defaultCondition = "deleted = :deleted and tenant_id = :tenantId"
)
@Filter(name = TenantBasedSoftDeletedEntity.DEFAULT_FILTER)
public abstract class TenantBasedSoftDeletedEntity extends BaseEntity implements TenantAwareEntity, SoftDeletableEntity {
    public static final String DEFAULT_FILTER = "TenantBasedSoftDeletedEntityFilter";
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
