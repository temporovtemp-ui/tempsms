package com.example.tempsms.services;

import com.example.tempsms.entities.Client;
import com.example.tempsms.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client create(Client client) {
        client.setId(null);
        return repository.save(client);
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    public Collection<Client> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Client> update(Long id, Client updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setPhone(updated.getPhone());
            return repository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}