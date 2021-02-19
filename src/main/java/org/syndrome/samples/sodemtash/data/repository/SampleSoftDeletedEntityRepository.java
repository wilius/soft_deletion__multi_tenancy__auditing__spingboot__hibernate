package org.syndrome.samples.sodemtash.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syndrome.samples.sodemtash.data.entity.SampleSoftDeletedEntity;

public interface SampleSoftDeletedEntityRepository extends JpaRepository<SampleSoftDeletedEntity, Long> {
}
