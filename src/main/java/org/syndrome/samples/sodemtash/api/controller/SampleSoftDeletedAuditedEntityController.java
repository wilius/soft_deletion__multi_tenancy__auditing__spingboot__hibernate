package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleSoftDeletedAuditedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleSoftDeletedAuditedEntityRepository;

import java.util.List;

@RestController
@RequestMapping("/sample-soft-deleted-audited-entity")
public class SampleSoftDeletedAuditedEntityController {

    private final SampleSoftDeletedAuditedEntityRepository repository;

    public SampleSoftDeletedAuditedEntityController(SampleSoftDeletedAuditedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleSoftDeletedAuditedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
