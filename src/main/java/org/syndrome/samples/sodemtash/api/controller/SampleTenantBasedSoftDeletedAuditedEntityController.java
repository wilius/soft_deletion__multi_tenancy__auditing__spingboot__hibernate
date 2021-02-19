package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedSoftDeletedAuditedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleTenantBasedSoftDeletedAuditedEntityRepository;
import org.syndrome.samples.sodemtash.tenant.annotations.TenantAwareContext;

import java.util.List;

@RestController
@TenantAwareContext
@RequestMapping("/sample-tenant-based-soft-deleted-audited-entity")
public class SampleTenantBasedSoftDeletedAuditedEntityController {
    private final SampleTenantBasedSoftDeletedAuditedEntityRepository repository;

    public SampleTenantBasedSoftDeletedAuditedEntityController(SampleTenantBasedSoftDeletedAuditedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleTenantBasedSoftDeletedAuditedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
