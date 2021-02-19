package org.syndrome.samples.sodemtash.api.controller;

import org.springframework.web.bind.annotation.*;
import org.syndrome.samples.sodemtash.data.entity.SampleSoftDeletedEntity;
import org.syndrome.samples.sodemtash.data.repository.SampleSoftDeletedEntityRepository;

import java.util.List;

@RestController
@RequestMapping("/sample-soft-deleted-entity")
public class SampleSoftDeletedEntityController {
    private final SampleSoftDeletedEntityRepository repository;

    public SampleSoftDeletedEntityController(SampleSoftDeletedEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SampleSoftDeletedEntity> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
