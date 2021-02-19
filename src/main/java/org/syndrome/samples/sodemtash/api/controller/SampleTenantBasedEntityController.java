package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleTenantBasedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleTenantBasedEntityRepository;
import org.syndrome.samples.sodemtash.tenant.annotations.TenantAwareContext;

import java.util.List;

@RestController
@TenantAwareContext
@RequestMapping("/sample-tenant-based-entity")
public class SampleTenantBasedEntityController {
    private final SampleTenantBasedEntityRepository repository;

    public SampleTenantBasedEntityController(SampleTenantBasedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleTenantBasedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
