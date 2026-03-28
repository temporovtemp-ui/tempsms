package com.example.tempsms.repositories;

import com.example.tempsms.models.Funnel;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FunnelRepository {
    private final Map<Long, Funnel> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Funnel save(Funnel funnel) {
        if (funnel.getId() == null) {
            funnel.setId(idGenerator.getAndIncrement());
        }
        storage.put(funnel.getId(), funnel);
        return funnel;
    }

    public Funnel findById(Long id) {
        return storage.getOrDefault(id, null);
    }

    public Collection<Funnel> findAll() {
        return storage.values();
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }
}
