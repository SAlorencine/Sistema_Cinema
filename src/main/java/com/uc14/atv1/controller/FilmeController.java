package com.uc14.atv1.controller;

import com.uc14.atv1.model.Filme;
import com.uc14.atv1.model.Analise;
import com.uc14.atv1.repository.FilmeRepository;
import com.uc14.atv1.repository.AnaliseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // Adicione isso

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {
    
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

 
    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        
        return filme.map(f -> ResponseEntity.ok(f))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Filme> salvarFilme(@RequestBody Filme filme) { 
        Filme novoFilme = filmeRepository.save(filme);
        return ResponseEntity.status(201).body(novoFilme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme filmeDetalhes) {
        return filmeRepository.findById(id)
            .map(filme -> {
                filme.setTitulo(filmeDetalhes.getTitulo());
                filme.setSinopse(filmeDetalhes.getSinopse()); 
                filme.setGenero(filmeDetalhes.getGenero());   
                filme.setAnoLancamento(filmeDetalhes.getAnoLancamento()); 
                
                Filme filmeAtualizado = filmeRepository.save(filme);
                return ResponseEntity.ok(filmeAtualizado);
            }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFilme(@PathVariable Long id) {
        return filmeRepository.findById(id)
            .map(filme -> {
                filmeRepository.delete(filme);
                return ResponseEntity.noContent().build();
            }).orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping("/{id}/analise")
    public ResponseEntity<Analise> salvarAnalise(@PathVariable Long id, @RequestBody Analise analise) {
        Optional<Filme> filmeOpt = filmeRepository.findById(id);
        
        if (filmeOpt.isPresent()) {
            analise.setFilme(filmeOpt.get()); 
            Analise novaAnalise = analiseRepository.save(analise);
            return ResponseEntity.status(201).body(novaAnalise);
        }
        
        return ResponseEntity.notFound().build();
    }
}