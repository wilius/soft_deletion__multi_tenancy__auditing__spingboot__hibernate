package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedSoftDeletedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleTenantBasedSoftDeletedEntityRepository;
import org.syndrome.samples.sodemtash.tenant.annotations.TenantAwareContext;

import java.util.List;

@RestController
@TenantAwareContext
@RequestMapping("/sample-tenant-based-soft-deleted-entity")
public class SampleTenantBasedSoftDeletedEntityController {
    private final SampleTenantBasedSoftDeletedEntityRepository repository;

    public SampleTenantBasedSoftDeletedEntityController(SampleTenantBasedSoftDeletedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleTenantBasedSoftDeletedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
