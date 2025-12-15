package com.uc14.atv1.repository;

import com.uc14.atv1.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnaliseRepository extends JpaRepository<Analise, Long> {
    List<Analise> findByFilmeId(Long filmeId);
}