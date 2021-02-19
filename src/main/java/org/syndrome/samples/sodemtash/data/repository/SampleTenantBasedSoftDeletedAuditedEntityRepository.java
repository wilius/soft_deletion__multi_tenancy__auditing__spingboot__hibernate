package org.syndrome.samples.sodemtash.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedSoftDeletedAuditedEntity;

public interface SampleTenantBasedSoftDeletedAuditedEntityRepository extends JpaRepository<SampleTenantBasedSoftDeletedAuditedEntity, Long> {
}
