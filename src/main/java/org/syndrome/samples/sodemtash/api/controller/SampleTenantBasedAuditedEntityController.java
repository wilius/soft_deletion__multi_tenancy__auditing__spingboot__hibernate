package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleSoftDeletedEntity;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedAuditedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleSoftDeletedEntityRepository;
import org.syndrome.samples.sodemtash.data.repository.SampleTenantBasedAuditedEntityRepository;
import org.syndrome.samples.sodemtash.tenant.annotations.TenantAwareContext;

import java.util.List;

@RestController
@TenantAwareContext
@RequestMapping("/sample-tenant-based-audited-entity")
public class SampleTenantBasedAuditedEntityController {
    private final SampleTenantBasedAuditedEntityRepository repository;

    public SampleTenantBasedAuditedEntityController(SampleTenantBasedAuditedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleTenantBasedAuditedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
