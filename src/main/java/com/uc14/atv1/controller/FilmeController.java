package com.uc14.atv1.controller;

import com.uc14.atv1.model.Filme;
import com.uc14.atv1.model.Analise;
import com.uc14.atv1.repository.FilmeRepository;
import com.uc14.atv1.repository.AnaliseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // <-- ESSA LINHA CORRIGE O ERRO
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica que é um controlador REST
@RequestMapping("/api/filmes") // URL base para todos os endpoints deste controlador
public class FilmeController {

    @Autowired // Injeção de dependência dos repositórios
    private FilmeRepository filmeRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    // --------------------------------------------------------------------------
    // ENDPOINTS DE FILMES
    // --------------------------------------------------------------------------

    /**
     * Requisito GET: Listar todos os filmes
     * URL: GET /api/filmes
     */
    @GetMapping
    public List<Filme> getAllFilmes() {
        return filmeRepository.findAll();
    }

    /**
     * Requisito GET: Buscar filme por ID
     * URL: GET /api/filmes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Filme> getFilmeById(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        if (filme.isPresent()) {
            return ResponseEntity.ok(filme.get()); // Retorna 200 OK com o filme
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }

    /**
     * Requisito POST: Criar um novo filme
     * URL: POST /api/filmes
     */
    @PostMapping
    public Filme createFilme(@RequestBody Filme novoFilme) {
        return filmeRepository.save(novoFilme); // Retorna 200/201 com o filme criado
    }

    /**
     * Requisito PUT: Atualizar um filme existente
     * URL: PUT /api/filmes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Filme> updateFilme(@PathVariable Long id, @RequestBody Filme filmeDetalhes) {
        return filmeRepository.findById(id)
            .map(filmeExistente -> {
                // Atualiza os campos do filme existente
                filmeExistente.setTitulo(filmeDetalhes.getTitulo());
                filmeExistente.setDiretor(filmeDetalhes.getDiretor());
                filmeExistente.setAnoLancamento(filmeDetalhes.getAnoLancamento());
                filmeExistente.setGenero(filmeDetalhes.getGenero());
                Filme atualizado = filmeRepository.save(filmeExistente);
                return ResponseEntity.ok(atualizado); // Retorna 200 OK
            }).orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found se o ID não existir
    }

    /**
     * Requisito DELETE: Deletar um filme
     * URL: DELETE /api/filmes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilme(@PathVariable Long id) {
        if (filmeRepository.existsById(id)) {
            filmeRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso, sem corpo)
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }
    
    // --------------------------------------------------------------------------
    // ENDPOINTS DE ANÁLISES
    // --------------------------------------------------------------------------
    
    /**
     * Requisito GET: Listar análises de um filme específico
     * URL: GET /api/filmes/{filmeId}/analises
     */
    @GetMapping("/{filmeId}/analises")
    public List<Analise> getAnalisesByFilme(@PathVariable Long filmeId) {
        return analiseRepository.findByFilmeId(filmeId);
    }
    
    /**
     * Requisito POST: Adicionar análise a um filme
     * URL: POST /api/filmes/{filmeId}/analises
     */
    @PostMapping("/{filmeId}/analises")
    public ResponseEntity<Analise> createAnalise(@PathVariable Long filmeId, @RequestBody Analise novaAnalise) {
        return filmeRepository.findById(filmeId)
            .map(filme -> {
                novaAnalise.setFilme(filme); // Associa a análise ao filme encontrado
                Analise salva = analiseRepository.save(novaAnalise);
                return ResponseEntity.ok(salva); // Retorna 200/201 OK com a análise criada
            }).orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found
    }
}