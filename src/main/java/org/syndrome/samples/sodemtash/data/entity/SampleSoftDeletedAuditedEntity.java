package org.syndrome.samples.sodemtash.data.entity;

import org.syndrome.samples.sodemtash.data.entity.base.BaseEntity;
import org.syndrome.samples.sodemtash.data.entity.base.SoftDeletedAuditedEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "soft_deleted_audited_entity")
@SequenceGenerator(
        name = BaseEntity.DEFAULT_ID_GENERATOR_NAME,
        sequenceName = "soft_deleted_audited_entity_seq",
        allocationSize = 1)
public class SampleSoftDeletedAuditedEntity extends SoftDeletedAuditedEntity {
}
