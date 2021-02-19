package org.syndrome.samples.sodemtash.data.entity.base;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
@FilterDef(
        name = SoftDeletedAuditedEntity.DEFAULT_FILTER,
        parameters = {
                @ParamDef(name = "deleted", type = "boolean"),
        },
        defaultCondition = "deleted = :deleted"
)
@Filter(name = SoftDeletedAuditedEntity.DEFAULT_FILTER)
@EntityListeners(AuditingEntityListener.class)
public abstract class SoftDeletedAuditedEntity extends BaseEntity implements SoftDeletableEntity {
    public static final String DEFAULT_FILTER = "SoftDeletedAuditedEntityFilter";

    private Boolean deleted;

    @PrePersist
    protected void onPrePersist() {
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
}
