package org.syndrome.samples.sodemtash.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedAuditedEntity;

public interface SampleTenantBasedAuditedEntityRepository extends JpaRepository<SampleTenantBasedAuditedEntity, Long> {
}
