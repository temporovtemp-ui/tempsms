package com.example.tempsms.services;

import com.example.tempsms.entities.Client;
import com.example.tempsms.entities.User;
import com.example.tempsms.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        return repository.save(user);
    }

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    public Collection<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<User> update(Long id, User user) {
        return repository.findById(id).map(existing -> {
            existing.setUsername(user.getUsername());
            existing.setPasswordHash(user.getPasswordHash());
            existing.setRole(user.getRole());
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
