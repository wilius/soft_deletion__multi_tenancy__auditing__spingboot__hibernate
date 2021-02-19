package org.syndrome.samples.sodemtash.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syndrome.samples.sodemtash.data.entity.SampleSoftDeletedAuditedEntity;

public interface SampleSoftDeletedAuditedEntityRepository extends JpaRepository<SampleSoftDeletedAuditedEntity, Long> {
}
