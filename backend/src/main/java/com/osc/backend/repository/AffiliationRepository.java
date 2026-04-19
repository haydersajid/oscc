package com.osc.backend.repository;

import com.osc.backend.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByCode(String code);
}
