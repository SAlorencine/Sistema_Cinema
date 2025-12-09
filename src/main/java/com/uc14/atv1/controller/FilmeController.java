package com.uc14.atv1.controller;

import com.uc14.atv1.model.Analise;
import com.uc14.atv1.model.Filme;
import com.uc14.atv1.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        return "lista";
    }

    @GetMapping("/cadastrar")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String salvarFilme(@ModelAttribute Filme filme) {
        filmeService.cadastrar(filme);
        return "redirect:/filmes";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        Filme filme = filmeService.buscarPorId(id);
        model.addAttribute("filme", filme);
        model.addAttribute("novaAnalise", new Analise());
        return "detalhes";
    }

    @PostMapping("/detalhes/{id}/analise")
    public String salvarAnalise(@PathVariable Long id, @ModelAttribute Analise analise) {
        filmeService.adicionarAnalise(id, analise);
        return "redirect:/filmes/detalhes/" + id;
    }
}