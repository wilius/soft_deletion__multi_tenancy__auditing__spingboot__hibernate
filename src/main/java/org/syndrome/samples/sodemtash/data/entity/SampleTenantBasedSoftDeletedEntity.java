package org.syndrome.samples.sodemtash.data.entity;

import org.syndrome.samples.sodemtash.data.entity.base.BaseEntity;
import org.syndrome.samples.sodemtash.data.entity.base.TenantBasedSoftDeletedEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sample_tenant_based_soft_deleted_entity")
@SequenceGenerator(
        name = BaseEntity.DEFAULT_ID_GENERATOR_NAME,
        sequenceName = "sample_tenant_based_soft_deleted_entity_seq",
        allocationSize = 1)
public class SampleTenantBasedSoftDeletedEntity extends TenantBasedSoftDeletedEntity {
}
