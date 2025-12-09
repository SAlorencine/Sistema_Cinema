package com.uc14.atv1.service;

import com.uc14.atv1.model.Analise;
import com.uc14.atv1.model.Filme;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmeService {
    private List<Filme> filmes = new ArrayList<>();
    private Long proximoId = 1L;

    public List<Filme> listarTodos() {
        return filmes;
    }

    public void cadastrar(Filme filme) {
        filme.setId(proximoId++);
        filmes.add(filme);
    }

    public Filme buscarPorId(Long id) {
        return filmes.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void adicionarAnalise(Long idFilme, Analise analise) {
        Filme filme = buscarPorId(idFilme);
        if (filme != null) {
            filme.getAnalises().add(analise);
        }
    }
}