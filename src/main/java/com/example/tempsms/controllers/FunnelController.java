package com.example.tempsms.controllers;

import com.example.tempsms.models.Client;
import com.example.tempsms.models.Funnel;
import com.example.tempsms.repositories.FunnelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/funnels")
public class FunnelController {
    private final FunnelRepository repository;

    public FunnelController(FunnelRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Funnel> createFunnel(@RequestBody Funnel funnel) {
        funnel.setId(null);
        Funnel created = repository.save(funnel);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<Collection<Funnel>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funnel> getById(@PathVariable Long id) {
        Funnel funnel = repository.findById(id);
        return funnel != null
                ? ResponseEntity.ok(funnel)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funnel> updateById(@PathVariable Long id, @RequestBody Funnel funnel) {
        Funnel existing = repository.findById(id);
        if (existing == null)
            return ResponseEntity.notFound().build();
        existing.setName(funnel.getName());
        existing.setDescription(funnel.getDescription());
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
