package org.syndrome.samples.sodemtash.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedEntity;

public interface SampleTenantBasedEntityRepository extends JpaRepository<SampleTenantBasedEntity, Long> {
}
