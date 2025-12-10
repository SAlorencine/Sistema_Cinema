package com.uc14.atv1.controller;

import com.uc14.atv1.model.Filme;
import com.uc14.atv1.model.Analise;
import com.uc14.atv1.repository.FilmeRepository;
import com.uc14.atv1.repository.AnaliseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Importante: Controller, não RestController
import org.springframework.ui.Model; // Usado para enviar dados para o HTML
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller // Mudamos de @RestController para @Controller para retornar HTML
@RequestMapping("/filmes") // URL base alinhada com os links do seu HTML
public class FilmeController {
    
    @GetMapping
    public String listarFilmes(Model model) {
        // Busca todos os filmes e adiciona ao "model" para o Thymeleaf usar
        model.addAttribute("filmes", filmeRepository.findAll());
        return "lista"; // Nome do arquivo HTML da lista (sem .html)
    }
    
    @GetMapping("/") // Mapeia a raiz (o endereço http://localhost:8080/)
    public String redirectToFilmes() {
    return "redirect:/filmes"; // Redireciona o navegador para a lista de filmes
}

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    // --------------------------------------------------------------------------
    // LISTAGEM (Página Inicial)
    // --------------------------------------------------------------------------
    

    // --------------------------------------------------------------------------
    // CADASTRO DE FILME
    // --------------------------------------------------------------------------
    
    // Passo 1: Abrir o formulário
    @GetMapping("/cadastrar")
    public String formCadastro(Model model) {
        model.addAttribute("filme", new Filme()); // Objeto vazio para o formulário preencher
        return "cadastro"; // Nome do arquivo HTML de cadastro
    }

    // Passo 2: Receber os dados do formulário e salvar
    @PostMapping("/cadastrar")
    public String salvarFilme(@ModelAttribute Filme filme) { // @ModelAttribute pega dados de Form HTML
        filmeRepository.save(filme);
        return "redirect:/filmes"; // Redireciona de volta para a lista após salvar
    }

    // --------------------------------------------------------------------------
    // DETALHES E ANÁLISE
    // --------------------------------------------------------------------------

    // Passo 1: Ver detalhes e lista de análises
    @GetMapping("/detalhes/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        Optional<Filme> filmeOpt = filmeRepository.findById(id);
        
        if (filmeOpt.isPresent()) {
            Filme filme = filmeOpt.get();
            model.addAttribute("filme", filme);
            model.addAttribute("novaAnalise", new Analise()); // Objeto vazio para o form de análise
            return "detalhes"; // Nome do arquivo HTML de detalhes
        }
        return "redirect:/filmes"; // Se não achar o filme, volta pra lista
    }

    // Passo 2: Salvar a análise enviada pelo form da página de detalhes
    @PostMapping("/detalhes/{id}/analise")
    public String salvarAnalise(@PathVariable Long id, @ModelAttribute Analise analise) {
        Optional<Filme> filmeOpt = filmeRepository.findById(id);
        
        if (filmeOpt.isPresent()) {
            analise.setFilme(filmeOpt.get()); // Relaciona a análise ao filme
            analiseRepository.save(analise);
            return "redirect:/filmes/detalhes/" + id; // Recarrega a página de detalhes para mostrar a nova nota
        }
        return "redirect:/filmes";
    }
}