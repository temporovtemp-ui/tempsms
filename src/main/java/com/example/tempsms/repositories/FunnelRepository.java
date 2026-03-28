package com.example.tempsms.repositories;

import com.example.tempsms.entities.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {}
