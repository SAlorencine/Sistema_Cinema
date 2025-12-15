package com.uc14.atv1.repository;

import com.uc14.atv1.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
}