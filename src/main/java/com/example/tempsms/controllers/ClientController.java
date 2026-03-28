package com.example.tempsms.controllers;

import com.example.tempsms.models.Client;
import com.example.tempsms.repositories.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientRepository repository;

    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        client.setId(null);
        Client created = repository.save(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<Collection<Client>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        Client client = repository.findById(id);
        return client != null
                ? ResponseEntity.ok(client)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateById(@PathVariable Long id, @RequestBody Client client) {
        Client existing = repository.findById(id);
        if (existing == null)
            return ResponseEntity.notFound().build();
        existing.setName(client.getName());
        existing.setPhone(client.getPhone());
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
