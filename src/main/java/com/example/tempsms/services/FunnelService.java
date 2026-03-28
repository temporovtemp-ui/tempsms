package com.example.tempsms.services;

import com.example.tempsms.entities.Funnel;
import com.example.tempsms.repositories.FunnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FunnelService {

    private final FunnelRepository repository;

    public FunnelService(FunnelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Funnel create(Funnel funnel) {
        funnel.setId(null);
        return repository.save(funnel);
    }

    public Optional<Funnel> getById(Long id) {
        return repository.findById(id);
    }

    public Collection<Funnel> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Funnel> update(Long id, Funnel updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
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